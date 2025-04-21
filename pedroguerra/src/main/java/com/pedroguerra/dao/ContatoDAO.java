package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.Contato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    public void inserir(Contato contato) throws SQLException {
        String sql = "INSERT INTO Contato (codigo, telefone, email, fk_funcionario_matricula, fk_empresa_cnpj) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contato.getCodigo());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getFkFuncionarioMatricula());
            stmt.setString(5, contato.getFkEmpresaCnpj());
            stmt.executeUpdate();
        }
    }

    public void remover(String codigo) throws SQLException {
        String sql = "DELETE FROM Contato WHERE codigo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        }
    }

    public List<Contato> listarPorFuncionario(String matricula) throws SQLException {
        String sql = "SELECT * FROM Contato WHERE fk_funcionario_matricula = ?";
        List<Contato> contatos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contatos.add(new Contato(
                            rs.getString("codigo"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("fk_funcionario_matricula"),
                            rs.getString("fk_empresa_cnpj")
                    ));
                }
            }
        }
        return contatos;
    }

    public List<Contato> listarPorEmpresa(String cnpj) throws SQLException {
        String sql = "SELECT * FROM Contato WHERE fk_empresa_cnpj = ?";
        List<Contato> contatos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contatos.add(new Contato(
                            rs.getString("codigo"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("fk_funcionario_matricula"),
                            rs.getString("fk_empresa_cnpj")
                    ));
                }
            }
        }
        return contatos;
    }
}
