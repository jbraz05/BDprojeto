
package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.model.Contato;
import com.pedroguerra.dto.EmpresaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void inserir(Empresa empresa, Contato contato) throws SQLException {
        String sql = "INSERT INTO Empresa (cnpj, nome, fk_endereco_cep) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, empresa.getNome());
            stmt.setString(3, empresa.getFkEnderecoCep());
            stmt.executeUpdate();
    
            if (contato != null) {
                contatoDAO.inserir(contato);
            }
        }
    }
    
    public void atualizar(Empresa empresa, Contato novoContato) throws SQLException {
        String sql = "UPDATE Empresa SET nome = ?, fk_endereco_cep = ? WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getFkEnderecoCep());
            stmt.setString(3, empresa.getCnpj());
            stmt.executeUpdate();
    
            if (novoContato != null) {
                contatoDAO.remover(novoContato.getCodigo()); // remove anterior
                contatoDAO.inserir(novoContato);
            }
        }
    }

    public void removerPorCnpj(String cnpj) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                List<Funcionario> funcionarios = funcionarioDAO.listarPorEmpresa(cnpj);
                for (Funcionario f : funcionarios) {
                    funcionarioDAO.removerPorMatricula(f.getMatricula());
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Atua WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Possui WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Empresa WHERE cnpj = ?")) {
                    stmt.setString(1, cnpj);
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

    public EmpresaDTO buscarPorCnpj(String cnpj) throws SQLException {
        String sql = "SELECT e.*, en.rua, en.numero, en.bairro, en.cidade FROM Empresa e JOIN Endereco en ON e.fk_endereco_cep = en.cep WHERE e.cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                EmpresaDTO dto = new EmpresaDTO();
                dto.setCnpj(rs.getString("cnpj"));
                dto.setNome(rs.getString("nome"));
                dto.setCep(rs.getString("fk_endereco_cep"));
                dto.setRua(rs.getString("rua"));
                dto.setNumero(rs.getString("numero"));
                dto.setBairro(rs.getString("bairro"));
                dto.setCidade(rs.getString("cidade"));

                List<Contato> contatos = contatoDAO.listarPorEmpresa(cnpj);
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }
                return dto;
            }
        }
        return null;
    }

    public List<EmpresaDTO> listarTodasComLocalizacao() throws SQLException {
        List<EmpresaDTO> lista = new ArrayList<>();
        String sql = """
            SELECT e.cnpj, e.nome,
                   en.cep, en.rua, en.numero, en.bairro, en.cidade,
                   l.nome_estado, l.nome_pais, l.regiao
            FROM Empresa e
            JOIN Endereco en ON e.fk_endereco_cep = en.cep
            LEFT JOIN Atua a ON e.cnpj = a.fk_Empresa_cnpj
            LEFT JOIN LocalizacaoAtuacao l ON l.codigo = a.fk_Localizacao_Atuacao_codigo
        """;
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                EmpresaDTO dto = new EmpresaDTO();
                dto.setCnpj(rs.getString("cnpj"));
                dto.setNome(rs.getString("nome"));
                dto.setCep(rs.getString("cep"));
                dto.setRua(rs.getString("rua"));
                dto.setNumero(rs.getString("numero"));
                dto.setBairro(rs.getString("bairro"));
                dto.setCidade(rs.getString("cidade"));
                dto.setNomeEstado(rs.getString("nome_estado"));
                dto.setNomePais(rs.getString("nome_pais"));
                dto.setRegiao(rs.getString("regiao"));
    
                // 🔥 Adicione isso aqui para garantir que email e telefone sejam preenchidos!
                List<Contato> contatos = contatoDAO.listarPorEmpresa(dto.getCnpj());
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }
    
                lista.add(dto);
            }
        }
    
        return lista;
    }

    public List<Empresa> listarTodas() throws SQLException {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empresa";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empresa e = new Empresa();
                e.setCnpj(rs.getString("cnpj"));
                e.setNome(rs.getString("nome"));
                lista.add(e);
            }
        }
        return lista;
    }
}