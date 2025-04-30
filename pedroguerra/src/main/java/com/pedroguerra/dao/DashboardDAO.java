package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardDAO {

    public Map<String, Integer> getTopClientesPorQuantidade() throws SQLException {
        String sql = """
            SELECT c.nome, COUNT(*) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON s.id = ct.nota_fiscal
            GROUP BY c.nome
            ORDER BY total DESC
            LIMIT 5
        """;

        Map<String, Integer> resultado = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getInt("total"));
            }
        }
        return resultado;
    }

    public Map<String, Double> getTopServicosPorValor() throws SQLException {
        String sql = """
            SELECT s.tipo, SUM(s.valor_medicao) AS total
            FROM Servico s
            GROUP BY s.tipo
            ORDER BY total DESC
            LIMIT 5
        """;

        Map<String, Double> resultado = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("tipo"), rs.getDouble("total"));
            }
        }
        return resultado;
    }
}
