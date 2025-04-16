package com.pedroguerra.dao;

import com.pedroguerra.model.Emprega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpregaDAO {

    public void inserir(Emprega emprega, Connection conn) throws SQLException {
        String sql = "INSERT INTO Emprega (fk_Empresa_cnpj, fk_Funcionario_matricula) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emprega.getFkEmpresaCnpj());
            stmt.setString(2, emprega.getFkFuncionarioMatricula());
            stmt.executeUpdate();
        }
    }
} 