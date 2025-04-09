package com.pedroguerra.dao;

import com.pedroguerra.model.Cliente;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class ClienteDAO {

    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (cnpj_cpf, nome, numero, bairro, rua) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCnpjCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getNumero());
            stmt.setString(4, cliente.getBairro());
            stmt.setString(5, cliente.getRua());
            stmt.executeUpdate();
        }
    }
}