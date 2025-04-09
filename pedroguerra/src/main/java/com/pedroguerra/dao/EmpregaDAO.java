package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class EmpregaDAO {

    public void inserir(String cnpj, String matricula) throws SQLException {
        String sql = "INSERT INTO Emprega (fk_Empresa_cnpj, fk_Funcionario_matricula) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setString(2, matricula);
            stmt.executeUpdate();
        }
    }
}