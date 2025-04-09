package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class VooPanoramicoDAO {

    public void inserir(String id, String operadorMatricula) throws SQLException {
        String sql = "INSERT INTO VooPanoramico (fk_Servico_id, fk_OperadorDrone_matricula) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, operadorMatricula);
            stmt.executeUpdate();
        }
    }
}
