package com.pedroguerra.dao;

import com.pedroguerra.model.Funcionario;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class FuncionarioDAO {

    public void inserir(Funcionario f) throws SQLException {
        String sql = "INSERT INTO Funcionario (matricula, nome, contato, cidade, numero, bairro, rua, fk_supervisor_matricula) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getMatricula());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getContato());
            stmt.setString(4, f.getCidade());
            stmt.setString(5, f.getNumero());
            stmt.setString(6, f.getBairro());
            stmt.setString(7, f.getRua());
            stmt.setString(8, f.getFkSupervisorMatricula());
            stmt.executeUpdate();
        }
    }
}