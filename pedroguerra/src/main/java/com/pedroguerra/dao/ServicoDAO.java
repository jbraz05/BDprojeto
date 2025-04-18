package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.Servico;
import com.pedroguerra.dto.ServicoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public boolean inserir(Servico servico) throws SQLException {
        String sql = "INSERT INTO Servico (id, data, tipo, fk_Funcionario_matricula) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getId());
            stmt.setDate(2, servico.getData());
            stmt.setString(3, servico.getTipo());
            stmt.setString(4, servico.getFkFuncionarioMatricula());
            stmt.executeUpdate();
            return true;
        }
    }

    public void atualizar(Servico servico) throws SQLException {
        String sql = "UPDATE Servico SET data = ?, tipo = ?, fk_Funcionario_matricula = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, servico.getData());
            stmt.setString(2, servico.getTipo());
            stmt.setString(3, servico.getFkFuncionarioMatricula());
            stmt.setString(4, servico.getId());
            stmt.executeUpdate();
        }
    }

    public boolean removerPorId(String id) throws SQLException {
        String sql = "DELETE FROM Servico WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public ServicoDTO buscarPorId(String id) throws SQLException {
        String sql = "SELECT s.*, f.nome AS nome_funcionario " +
                     "FROM Servico s " +
                     "JOIN Funcionario f ON s.fk_Funcionario_matricula = f.matricula " +
                     "WHERE s.id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ServicoDTO dto = new ServicoDTO();
                dto.setId(rs.getString("id"));
                dto.setData(rs.getDate("data"));
                dto.setTipo(rs.getString("tipo"));
                dto.setFkFuncionarioMatricula(rs.getString("fk_Funcionario_matricula"));
                dto.setNomeFuncionario(rs.getString("nome_funcionario"));
                return dto;
            } else {
                return null;
            }
        }
    }

    public List<ServicoDTO> listarTodosDTO() throws SQLException {
        List<ServicoDTO> lista = new ArrayList<>();
        String sql = "SELECT s.*, f.nome AS nome_funcionario " +
                     "FROM Servico s " +
                     "JOIN Funcionario f ON s.fk_Funcionario_matricula = f.matricula";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ServicoDTO dto = new ServicoDTO();
                dto.setId(rs.getString("id"));
                dto.setData(rs.getDate("data"));
                dto.setTipo(rs.getString("tipo"));
                dto.setFkFuncionarioMatricula(rs.getString("fk_Funcionario_matricula"));
                dto.setNomeFuncionario(rs.getString("nome_funcionario"));
                lista.add(dto);
            }
        }

        return lista;
    }
}
