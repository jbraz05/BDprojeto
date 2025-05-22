package com.pedroguerra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardInterativoProcedureDAO {

    private final Connection connection;

    public DashboardInterativoProcedureDAO(Connection connection) {
        this.connection = connection;
    }

    public Map<String, Double> obterDados(String entidade, String metrica) {
        Map<String, Double> dados = new LinkedHashMap<>();
        String sql = "CALL proc_dashboard_interativo(?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entidade);
            stmt.setString(2, metrica);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString(1); // Nome da entidade (empresa, cliente, funcionario)
                    double valor = rs.getDouble(2); // Valor agregado
                    dados.put(nome, valor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dados;
    }
}
