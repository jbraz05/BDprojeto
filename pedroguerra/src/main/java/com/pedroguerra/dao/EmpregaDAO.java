package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.Emprega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpregaDAO {

    public boolean inserir(Emprega emprega) throws SQLException {
        String sql = "INSERT INTO Emprega (fk_Empresa_cnpj, fk_Funcionario_matricula) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emprega.getFkEmpresaCnpj());
            stmt.setString(2, emprega.getFkFuncionarioMatricula());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Emprega> listarTodos() throws SQLException {
        List<Emprega> lista = new ArrayList<>();
        String sql = "SELECT * FROM Emprega";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                Emprega e = new Emprega();
                e.setFkEmpresaCnpj(rs.getString("fk_Empresa_cnpj"));
                e.setFkFuncionarioMatricula(rs.getString("fk_Funcionario_matricula"));
                lista.add(e);
            }
        }
        return lista;
    }

    public boolean remover(String matricula) throws SQLException {
        String sql = "DELETE FROM Emprega WHERE fk_Funcionario_matricula = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            return stmt.executeUpdate() > 0;
        }
    }
}