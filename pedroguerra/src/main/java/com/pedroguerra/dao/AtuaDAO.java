package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class AtuaDAO {

    public void inserir(String cnpj, String codigoLocal) throws SQLException {
        String sql = "INSERT INTO Atua (fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setString(2, codigoLocal);
            stmt.executeUpdate();
        }
    }

    public void atualizar(String cnpj, String novoCodigoLocalizacao) throws SQLException {
        String sql = "UPDATE Atua SET fk_Localizacao_Atuacao_codigo = ? WHERE fk_Empresa_cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, novoCodigoLocalizacao);
            stmt.setString(2, cnpj);
            stmt.executeUpdate();
        }
    }
}