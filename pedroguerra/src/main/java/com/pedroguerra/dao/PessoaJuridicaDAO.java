package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class PessoaJuridicaDAO {

    public void inserir(String cnpjCpf) throws SQLException {
        String sql = "INSERT INTO PessoaJuridica (fk_Cliente_cnpj_cpf) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpjCpf);
            stmt.executeUpdate();
        }
    }
}
