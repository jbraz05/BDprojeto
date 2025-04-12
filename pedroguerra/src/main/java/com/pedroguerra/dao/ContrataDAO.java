package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class ContrataDAO {

    public void inserir(String notaFiscal, String cnpjCpf) throws SQLException {
        String sql = "INSERT INTO Contrata (nota_fiscal, fk_Cliente_cnpj_cpf) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, notaFiscal);
            stmt.setString(2, cnpjCpf);
            stmt.executeUpdate();
        }
    }
}