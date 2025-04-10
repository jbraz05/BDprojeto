package com.pedroguerra.dao;

import com.pedroguerra.model.LocalizacaoAtuacao;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalizacaoAtuacaoDAO {
    public void inserir(LocalizacaoAtuacao l) throws SQLException {
        String sql = "INSERT INTO LocalizacaoAtuacao (codigo, nome_pais, nome_estado, regiao) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getCodigo());
            stmt.setString(2, l.getNomePais());
            stmt.setString(3, l.getNomeEstado());
            stmt.setString(4, l.getRegiao());
            stmt.executeUpdate();
        }
    }

        public List<LocalizacaoAtuacao> listarTodas() throws SQLException {
        String sql = "SELECT * FROM LocalizacaoAtuacao";
        List<LocalizacaoAtuacao> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LocalizacaoAtuacao l = new LocalizacaoAtuacao(
                        rs.getString("codigo"),
                        rs.getString("nome_pais"),
                        rs.getString("nome_estado"),
                        rs.getString("regiao")
                );
                lista.add(l);
            }
        }
        return lista;
    }

    public void removerPorCodigo(String codigo) throws SQLException {
        String sql = "DELETE FROM LocalizacaoAtuacao WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        }
    }

    public LocalizacaoAtuacao buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM LocalizacaoAtuacao WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new LocalizacaoAtuacao(
                        rs.getString("codigo"),
                        rs.getString("nome_pais"),
                        rs.getString("nome_estado"),
                        rs.getString("regiao")
                );
            }
        }
        return null;
    }

    public void atualizar(LocalizacaoAtuacao l) throws SQLException {
        String sql = "UPDATE LocalizacaoAtuacao SET nome_pais = ?, nome_estado = ?, regiao = ? WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getNomePais());
            stmt.setString(2, l.getNomeEstado());
            stmt.setString(3, l.getRegiao());
            stmt.setString(4, l.getCodigo());
            stmt.executeUpdate();
        }
    }
}