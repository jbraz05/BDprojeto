package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class MapeamentoTradicionalDAO {

    public void inserir(String id) throws SQLException {
        String sql = "INSERT INTO MapeamentoTradicional (fk_Servico_id) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}
