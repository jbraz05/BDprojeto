
package com.pedroguerra.dao;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.config.ConnectionFactory;

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
    public List<Empresa> listarTodas() throws SQLException {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empresa";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setNome(rs.getString("nome"));
                empresa.setContato(rs.getString("contato"));
                empresa.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                lista.add(empresa); 
            }
        }
        return lista;
    }
    
    public void removerPorCnpj(String cnpj) throws SQLException {
    try (Connection conn = ConnectionFactory.getConnection()) {
        conn.setAutoCommit(false); // inicia transação

        try {
            // 1. Buscar todos os funcionários da empresa
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            List<Funcionario> funcionarios = funcionarioDAO.listarPorEmpresa(cnpj);

            // 2. Remover cada funcionário
            for (Funcionario f : funcionarios) {
                funcionarioDAO.removerPorMatricula(f.getMatricula());
            }

            // 3. Apagar vínculos na tabela Atua e Possui (se aplicável)
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Atua WHERE fk_Empresa_cnpj = ?")) {
                stmt.setString(1, cnpj);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Possui WHERE fk_Empresa_cnpj = ?")) {
                stmt.setString(1, cnpj);
                stmt.executeUpdate();
            }

            // 4. Apagar empresa
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
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setCnpj(rs.getString("cnpj"));
                    empresa.setNome(rs.getString("nome"));
                    empresa.setContato(rs.getString("contato"));
                    empresa.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                    return empresa;
                }
            }
        }
        return null;
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
    public List<String[]> listarTodasComLocalizacao() throws SQLException {
        List<String[]> lista = new ArrayList<>();
    
        String sql = """
            SELECT e.cnpj, e.nome, e.contato,
                   en.rua, en.numero, en.bairro, en.cidade,
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
                String[] linha = new String[10];
                linha[0] = rs.getString("cnpj");
                linha[1] = rs.getString("nome");
                linha[2] = rs.getString("contato");
                linha[3] = rs.getString("rua");
                linha[4] = rs.getString("numero");
                linha[5] = rs.getString("bairro");
                linha[6] = rs.getString("cidade");
                linha[7] = rs.getString("nome_estado");
                linha[8] = rs.getString("nome_pais");
                linha[9] = rs.getString("regiao");
                lista.add(linha);
            }
        }
    
        return lista;
    }
    
    public Empresa buscarPorFuncionario(String matricula) throws SQLException {
        String sql = """
            SELECT e.*
            FROM Empresa e
            JOIN Emprega emp ON emp.fk_Empresa_cnpj = e.cnpj
            WHERE emp.fk_Funcionario_matricula = ?
        """;
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setCnpj(rs.getString("cnpj"));
                    empresa.setNome(rs.getString("nome"));
                    empresa.setContato(rs.getString("contato"));
                    empresa.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                    return empresa;
                }
            }
        }
    
        return null;
    }
    
}
