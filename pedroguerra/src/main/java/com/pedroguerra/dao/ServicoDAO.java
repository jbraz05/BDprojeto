package com.pedroguerra.dao;

import com.pedroguerra.model.Servico;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class ServicoDAO {

    public void inserir(Servico servico) throws SQLException {
        String sql = "INSERT INTO Servico (id, data, tipo, fk_Funcionario_matricula) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getId());
            stmt.setDate(2, new java.sql.Date(servico.getData().getTime()));
            stmt.setString(3, servico.getTipo());
            stmt.setString(4, servico.getfkMatriculaFuncionario());
            stmt.executeUpdate();
        }
    }
}