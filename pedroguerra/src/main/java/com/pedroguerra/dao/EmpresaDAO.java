package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.dto.EmpresaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    public void inserir(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO Empresa (cnpj, nome, contato, fk_endereco_cep) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, empresa.getNome());
            stmt.setString(3, empresa.getContato());
            stmt.setString(4, empresa.getFkEnderecoCep());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Empresa empresa) throws SQLException {
        String sql = "UPDATE Empresa SET nome = ?, contato = ?, fk_endereco_cep = ? WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getContato());
            stmt.setString(3, empresa.getFkEnderecoCep());
            stmt.setString(4, empresa.getCnpj());
            stmt.executeUpdate();
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

    public Empresa buscarPorCnpj(String cnpj) throws SQLException {
        String sql = "SELECT * FROM Empresa WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setNome(rs.getString("nome"));
                empresa.setContato(rs.getString("contato"));
                empresa.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                return empresa;
            }
        }
        return null;
    }

    public List<EmpresaDTO> listarTodasComLocalizacao() throws SQLException {
        List<EmpresaDTO> lista = new ArrayList<>();
        String sql = """
            SELECT e.cnpj, e.nome, e.contato,
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
                dto.setContato(rs.getString("contato"));
                dto.setCep(rs.getString("cep"));
                dto.setRua(rs.getString("rua"));
                dto.setNumero(rs.getString("numero"));
                dto.setBairro(rs.getString("bairro"));
                dto.setCidade(rs.getString("cidade"));
                dto.setNomeEstado(rs.getString("nome_estado"));
                dto.setNomePais(rs.getString("nome_pais"));
                dto.setRegiao(rs.getString("regiao"));
                lista.add(dto);
            }
        }
        return lista;
    }
}
