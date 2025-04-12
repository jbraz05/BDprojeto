package com.pedroguerra.dao;

import com.pedroguerra.model.Funcionario;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void inserir(Funcionario f) throws SQLException {
        String sql = "INSERT INTO Funcionario (matricula, nome, contato, cidade, numero, bairro, rua, fk_supervisor_matricula) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getMatricula());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getContato());
            stmt.setString(4, f.getCidade());
            stmt.setString(5, f.getNumero());
            stmt.setString(6, f.getBairro());
            stmt.setString(7, f.getRua());

            // Aqui é onde fazemos o tratamento do supervisor opcional:
            if (f.getFkSupervisorMatricula() == null || f.getFkSupervisorMatricula().trim().isEmpty()) {
                stmt.setNull(8, Types.VARCHAR); // Envia NULL ao banco
            } else {
                stmt.setString(8, f.getFkSupervisorMatricula());
            }

            stmt.executeUpdate();
        }
    }

    public List<Funcionario> listarPorEmpresa(String cnpj) throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = """
            SELECT f.*
            FROM Funcionario f
            JOIN Emprega e ON f.matricula = e.fk_Funcionario_matricula
            WHERE e.fk_Empresa_cnpj = ?
        """;
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setMatricula(rs.getString("matricula"));
                    f.setNome(rs.getString("nome"));
                    f.setContato(rs.getString("contato"));
                    f.setCidade(rs.getString("cidade"));
                    f.setNumero(rs.getString("numero"));
                    f.setBairro(rs.getString("bairro"));
                    f.setRua(rs.getString("rua"));
                    f.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
                    lista.add(f);
                }
            }
        }
        return lista;
    }
    
    public void removerPorMatricula(String matricula) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // inicia transação
    
            try {
                // Remove de tabelas filhas primeiro
                try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Engenheiro WHERE fk_funcionario_matricula = ?")) {
                    stmt1.setString(1, matricula);
                    stmt1.executeUpdate();
                }
                try (PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Socio WHERE fk_funcionario_matricula = ?")) {
                    stmt2.setString(1, matricula);
                    stmt2.executeUpdate();
                }
                try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM OperadorDrone WHERE fk_funcionario_matricula = ?")) {
                    stmt3.setString(1, matricula);
                    stmt3.executeUpdate();
                }
    
                // Remove da tabela Emprega (evita erro de integridade referencial)
                try (PreparedStatement stmt4 = conn.prepareStatement("DELETE FROM Emprega WHERE fk_funcionario_matricula = ?")) {
                    stmt4.setString(1, matricula);
                    stmt4.executeUpdate();
                }
    
                // Remove quem ele supervisiona, se for o caso
                try (PreparedStatement stmt5 = conn.prepareStatement("UPDATE Funcionario SET fk_supervisor_matricula = NULL WHERE fk_supervisor_matricula = ?")) {
                    stmt5.setString(1, matricula);
                    stmt5.executeUpdate();
                }
    
                // Agora sim remove o funcionário
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Funcionario WHERE matricula = ?")) {
                    stmt.setString(1, matricula);
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
    public void atualizar(Funcionario f) throws SQLException {
        String sql = "UPDATE Funcionario SET nome = ?, contato = ?, cidade = ?, numero = ?, bairro = ?, rua = ?, fk_supervisor_matricula = ? WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getContato());
            stmt.setString(3, f.getCidade());
            stmt.setString(4, f.getNumero());
            stmt.setString(5, f.getBairro());
            stmt.setString(6, f.getRua());
    
            if (f.getFkSupervisorMatricula() == null || f.getFkSupervisorMatricula().trim().isEmpty()) {
                stmt.setNull(7, Types.VARCHAR);
            } else {
                stmt.setString(7, f.getFkSupervisorMatricula());
            }
    
            stmt.setString(8, f.getMatricula()); // WHERE matricula = ?
            stmt.executeUpdate();
        }
    }
    
}
