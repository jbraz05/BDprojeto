package com.pedroguerra.dao;

import com.pedroguerra.model.Endereco;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class EnderecoDAO {

    public void inserir(Endereco e) throws SQLException {
        String sql = "INSERT INTO Endereco (cep, numero, cidade, bairro, rua) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getCep());
            stmt.setString(2, e.getNumero());
            stmt.setString(3, e.getCidade());
            stmt.setString(4, e.getBairro());
            stmt.setString(5, e.getRua());
            stmt.executeUpdate();
        }
    }

    public Endereco buscarPorCep(String cep) throws SQLException {
        String sql = "SELECT * FROM Endereco WHERE cep = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cep);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Endereco(
                        rs.getString("cep"),
                        rs.getString("numero"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua")
                );
            }
        }
        return null;
    }

    public void atualizar(Endereco endereco) throws SQLException {
        String sql = "UPDATE Endereco SET rua = ?, numero = ?, bairro = ?, cidade = ? WHERE cep = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getCep());
            stmt.executeUpdate();
        }
    }

    public boolean cepExiste(String cep) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Endereco WHERE cep = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cep);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void remover(String cep) throws SQLException {
        String sql = "DELETE FROM Endereco WHERE cep = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cep);
            stmt.executeUpdate();
        }
    }
} 
