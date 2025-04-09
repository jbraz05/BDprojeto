package com.pedroguerra.dao;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class EmpresaDAO {

    public void inserir(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO Empresa (cnpj, nome, contato, numero, bairro, cidade, rua) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, empresa.getNome());
            stmt.setString(3, empresa.getContato());
            stmt.setString(4, empresa.getNumero());
            stmt.setString(5, empresa.getBairro());
            stmt.setString(6, empresa.getCidade());
            stmt.setString(7, empresa.getRua());
            stmt.executeUpdate();
        }
    }
}
