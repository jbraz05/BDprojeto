package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dto.ClienteDTO;
import com.pedroguerra.model.Cliente;
import com.pedroguerra.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(ClienteDTO dto) throws SQLException {
        String sql = "INSERT INTO Cliente (cnpj_cpf, nome, fk_Endereco_cep) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                if (!enderecoDAO.cepExiste(dto.getFkEnderecoCep())) {
                    Endereco endereco = new Endereco(
                        dto.getFkEnderecoCep(),
                        dto.getNumeroEndereco(),
                        dto.getCidadeEndereco(),
                        dto.getBairroEndereco(),
                        dto.getRuaEndereco()
                    );
                    enderecoDAO.inserir(endereco);
                }

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, dto.getCnpjCpf());
                    stmt.setString(2, dto.getNome());
                    stmt.setString(3, dto.getFkEnderecoCep());
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public boolean atualizar(ClienteDTO dto) throws SQLException {
        String sql = "UPDATE Cliente SET nome = ?, fk_Endereco_cep = ? WHERE cnpj_cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                if (!enderecoDAO.cepExiste(dto.getFkEnderecoCep())) {
                    Endereco novoEndereco = new Endereco(
                        dto.getFkEnderecoCep(),
                        dto.getNumeroEndereco(),
                        dto.getCidadeEndereco(),
                        dto.getBairroEndereco(),
                        dto.getRuaEndereco()
                    );
                    enderecoDAO.inserir(novoEndereco);
                }

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, dto.getNome());
                    stmt.setString(2, dto.getFkEnderecoCep());
                    stmt.setString(3, dto.getCnpjCpf());
                    stmt.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public boolean remover(String cnpjCpf) throws SQLException {
        String getCepSQL = "SELECT fk_Endereco_cep FROM Cliente WHERE cnpj_cpf = ?";
        String deleteContrataSQL = "DELETE FROM Contrata WHERE fk_Cliente_cnpj_cpf = ?";
        String deleteClienteSQL = "DELETE FROM Cliente WHERE cnpj_cpf = ?";
        String checkCepUsoSQL = "SELECT COUNT(*) FROM Cliente WHERE fk_Endereco_cep = ?";
        String deleteEnderecoSQL = "DELETE FROM Endereco WHERE cep = ?";
    
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String cep = null;
    
                //Busca o CEP antes de remover o cliente
                try (PreparedStatement stmt = conn.prepareStatement(getCepSQL)) {
                    stmt.setString(1, cnpjCpf);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        cep = rs.getString("fk_Endereco_cep");
                    }
                }
    
                //Remove registros da tabela Contrata
                try (PreparedStatement stmt = conn.prepareStatement(deleteContrataSQL)) {
                    stmt.setString(1, cnpjCpf);
                    stmt.executeUpdate();
                }
    
                //Remove o cliente
                try (PreparedStatement stmt = conn.prepareStatement(deleteClienteSQL)) {
                    stmt.setString(1, cnpjCpf);
                    stmt.executeUpdate();
                }
    
                //Verifica se o CEP está em uso por outro cliente antes de remover o endereço
                if (cep != null) {
                    try (PreparedStatement stmt = conn.prepareStatement(checkCepUsoSQL)) {
                        stmt.setString(1, cep);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next() && rs.getInt(1) == 0) {
                            try (PreparedStatement deleteEndereco = conn.prepareStatement(deleteEnderecoSQL)) {
                                deleteEndereco.setString(1, cep);
                                deleteEndereco.executeUpdate();
                            }
                        }
                    }
                }
    
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
    public Cliente buscarPorCnpjCpf(String cnpjCpf) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE cnpj_cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpjCpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getString("cnpj_cpf"),
                        rs.getString("nome"),
                        rs.getString("fk_Endereco_cep")
                    );
                }
            }
        }

        return null;
    }

    public List<ClienteDTO> listarTodos() throws SQLException {
        List<ClienteDTO> lista = new ArrayList<>();

        String sql = """
            SELECT c.cnpj_cpf, c.nome,
                   e.cep, e.numero, e.cidade, e.bairro, e.rua
            FROM Cliente c
            JOIN Endereco e ON c.fk_Endereco_cep = e.cep
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteDTO dto = new ClienteDTO();
                dto.setCnpjCpf(rs.getString("cnpj_cpf"));
                dto.setNome(rs.getString("nome"));
                dto.setFkEnderecoCep(rs.getString("cep"));
                dto.setNumeroEndereco(rs.getInt("numero"));
                dto.setCidadeEndereco(rs.getString("cidade"));
                dto.setBairroEndereco(rs.getString("bairro"));
                dto.setRuaEndereco(rs.getString("rua"));
                lista.add(dto);
            }
        }

        return lista;
    }
    public List<String> buscarIdsServicosDoCliente(String cnpjCpf) throws SQLException {
        List<String> ids = new ArrayList<>();
        String sql = "SELECT nota_fiscal FROM Contrata WHERE fk_Cliente_cnpj_cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpjCpf);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getString("nota_fiscal"));
            }
        }
        return ids;
    }
    
    public String buscarTipoServico(String idServico) throws SQLException {
        String tipo = null;
        String sql = "SELECT tipo FROM Servico WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idServico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tipo = rs.getString("tipo");
            }
        }
        return tipo;
    }
    
    // Versão que usa uma conexão existente (não fecha conexão!)
    public void removerPorConexao(String cnpjCpf, Connection conn) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE cnpj_cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpjCpf);
            stmt.executeUpdate();
        }
    }
    
}
