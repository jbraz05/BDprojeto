package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.math.BigDecimal;
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

    public Map<String, Integer> getStatusServicos() throws SQLException {
        String sql = """
            SELECT feito, COUNT(*) AS total
              FROM Servico
             GROUP BY feito
        """;

        Map<String, Integer> status = new LinkedHashMap<>();
        status.put("Concluídos", 0);
        status.put("Pendentes",  0);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                boolean feito = rs.getBoolean("feito");
                int total = rs.getInt("total");
                if (feito) {
                    status.put("Concluídos", total);
                } else {
                    status.put("Pendentes", total);
                }
            }
        }
        return status;
    }

    public Map<String, BigDecimal> getReceitaMensal(int ano) throws SQLException {
        String sql = """
            SELECT MONTH(data_emissao_medicao) AS mes,
                   SUM(valor_medicao)       AS total
              FROM Servico
             WHERE YEAR(data_emissao_medicao) = ?
             GROUP BY mes
             ORDER BY mes
        """;

        // Meses em ordem de Janeiro a Dezembro
        String[] meses = {
            "Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
            "Jul", "Ago", "Set", "Out", "Nov", "Dez"
        };

        // Inicializa com zero para garantir todos os meses
        Map<String, BigDecimal> receita = new LinkedHashMap<>();
        for (String m : meses) {
            receita.put(m, BigDecimal.ZERO);
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ano);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int numMes = rs.getInt("mes");           // 1–12
                    BigDecimal total = rs.getBigDecimal("total");
                    String nomeMes = meses[numMes - 1];
                    receita.put(nomeMes, total);
                }
            }
        }
        return receita;
    }
}
