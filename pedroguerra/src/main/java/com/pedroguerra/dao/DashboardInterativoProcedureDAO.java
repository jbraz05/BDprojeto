package com.pedroguerra.dao;

import java.math.BigDecimal;
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

    public Map<String, BigDecimal> getValorTotalPorEmpresa() throws SQLException {
        String sql = """
DELIMITER //

CREATE PROCEDURE proc_valor_total_por_empresa()
BEGIN
    SELECT e.nome, SUM(s.valor_medicao) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN Servico s ON p.fk_servico_id = s.id
    GROUP BY e.nome
    ORDER BY total DESC;
END //

DELIMITER ;
        """;

        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getBigDecimal("total"));
            }
        }

        return resultado;
    }

    public Map<String, BigDecimal> getQuantidadeServicosPorEmpresa() throws SQLException {
        String sql = """
DELIMITER //

CREATE PROCEDURE proc_quantidade_servicos_por_empresa()
BEGIN
    SELECT e.nome, COUNT(*) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    GROUP BY e.nome
    ORDER BY total DESC;
END //

DELIMITER ;
        """;

        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getBigDecimal("total"));
            }
        }

        return resultado;
    }

    public Map<String, BigDecimal> getAreaTotalPorEmpresa() throws SQLException {
        String sql = """
DELIMITER //

CREATE PROCEDURE proc_area_total_por_empresa()
BEGIN
    SELECT e.nome, SUM(r.area) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN RelatorioServico r ON p.fk_servico_id = r.fk_servico_id
    GROUP BY e.nome
    ORDER BY total DESC;
END //

DELIMITER ;
        """;

        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getBigDecimal("total"));
            }
        }

        return resultado;
    }

    public Map<String, BigDecimal> getServicosConcluidosPorEmpresa() throws SQLException {
        String sql = """
DELIMITER //

CREATE PROCEDURE proc_servicos_concluidos_por_empresa()
BEGIN
    SELECT e.nome, COUNT(*) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN Servico s ON p.fk_servico_id = s.id
    WHERE s.feito = TRUE
    GROUP BY e.nome
    ORDER BY total DESC;
END //

DELIMITER ;

        """;

        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getBigDecimal("total"));
            }
        }

        return resultado;
    }

    public Map<String, BigDecimal> getTempoMedioTotalPorEmpresa() throws SQLException {
        String sql = """
DELIMITER //

CREATE PROCEDURE proc_tempo_medio_total_por_empresa()
BEGIN
    SELECT e.nome,
           AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN Servico s ON p.fk_servico_id = s.id
    GROUP BY e.nome
    ORDER BY total DESC;
END //

DELIMITER ;
        """;

        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getBigDecimal("total"));
            }
        }

        return resultado;
    }
}
