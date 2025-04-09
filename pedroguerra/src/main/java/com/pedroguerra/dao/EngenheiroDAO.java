package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class EngenheiroDAO {

    public void inserir(String matricula) throws SQLException {
        String sql = "INSERT INTO Engenheiro (fk_Funcionario_matricula) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.executeUpdate();
        }
    }
}
