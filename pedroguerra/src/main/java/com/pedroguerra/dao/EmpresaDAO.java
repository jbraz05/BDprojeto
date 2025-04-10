package com.pedroguerra.dao;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Empresa> listarTodas() throws SQLException {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empresa";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setNome(rs.getString("nome"));
                empresa.setContato(rs.getString("contato"));
                empresa.setNumero(rs.getString("numero"));
                empresa.setBairro(rs.getString("bairro"));
                empresa.setCidade(rs.getString("cidade"));
                empresa.setRua(rs.getString("rua"));
                lista.add(empresa);
            }
        }
        return lista;
    }
    public void removerPorCnpj(String cnpj) throws SQLException {
        String sql = "DELETE FROM Empresa WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.executeUpdate();
        }
    }

    public Empresa buscarPorCnpj(String cnpj) throws SQLException {
        String sql = "SELECT * FROM Empresa WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setCnpj(rs.getString("cnpj"));
                    empresa.setNome(rs.getString("nome"));
                    empresa.setContato(rs.getString("contato"));
                    empresa.setNumero(rs.getString("numero"));
                    empresa.setBairro(rs.getString("bairro"));
                    empresa.setCidade(rs.getString("cidade"));
                    empresa.setRua(rs.getString("rua"));
                    return empresa;
                }
            }
        }
        return null;
    }
    

    public void atualizar(Empresa empresa) throws SQLException {
        String sql = "UPDATE Empresa SET nome = ?, contato = ?, numero = ?, bairro = ?, cidade = ?, rua = ? WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getContato());
            stmt.setString(3, empresa.getNumero());
            stmt.setString(4, empresa.getBairro());
            stmt.setString(5, empresa.getCidade());
            stmt.setString(6, empresa.getRua());
            stmt.setString(7, empresa.getCnpj());
    
            stmt.executeUpdate();
        }
    }
    
    
}
