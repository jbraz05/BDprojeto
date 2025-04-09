package com.pedroguerra.dao;

import com.pedroguerra.model.LocalizacaoAtuacao;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

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
}