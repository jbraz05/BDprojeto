package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class PossuiDAO {

    public void inserir(String servicoId, String codigoLocal, String cnpj) throws SQLException {
        String sql = "INSERT INTO Possui (fk_Servico_id, fk_Localizacao_Atuacao_codigo, fk_Empresa_cnpj) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicoId);
            stmt.setString(2, codigoLocal);
            stmt.setString(3, cnpj);
            stmt.executeUpdate();
        }
    }
}