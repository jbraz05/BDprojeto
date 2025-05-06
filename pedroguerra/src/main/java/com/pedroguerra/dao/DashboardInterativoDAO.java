package com.pedroguerra.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class DashboardInterativoDAO {

    private final Connection connection;

    public DashboardInterativoDAO(Connection connection) {
        this.connection = connection;
    }

    public Map<String, BigDecimal> getDadosPorEmpresa() throws SQLException {
        String sql = """
            SELECT e.nome, SUM(s.valor_medicao) AS total
            FROM Empresa e
            JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
            JOIN Servico s ON p.fk_servico_id = s.id
            GROUP BY e.nome
            ORDER BY total DESC
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

    public Map<String, BigDecimal> getDadosPorCliente() throws SQLException {
        String sql = """
            SELECT c.nome, SUM(s.valor_medicao) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON ct.nota_fiscal = s.id
            GROUP BY c.nome
            ORDER BY total DESC
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

    public Map<String, Number> getQuantidadeServicosPorEmpresa() throws SQLException {
        String sql = """
            SELECT e.nome, COUNT(s.id) AS total
            FROM Empresa e
            JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
            JOIN Servico s ON p.fk_servico_id = s.id
            GROUP BY e.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getValorTotalServicosPorEmpresa() throws SQLException {
        String sql = """
            SELECT e.nome, SUM(s.valor_medicao) AS total
            FROM Empresa e
            JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
            JOIN Servico s ON p.fk_servico_id = s.id
            GROUP BY e.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getQuantidadeServicosPorCliente() throws SQLException {
        String sql = """
            SELECT c.nome, COUNT(s.id) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON ct.nota_fiscal = s.id
            GROUP BY c.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getValorTotalServicosPorCliente() throws SQLException {
        String sql = """
            SELECT c.nome, SUM(s.valor_medicao) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON ct.nota_fiscal = s.id
            GROUP BY c.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    private Map<String, Number> executarConsultaSimples(String sql) throws SQLException {
        Map<String, Number> resultado = new LinkedHashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString(1), rs.getBigDecimal(2));
            }
        }
        return resultado;
    }
    
}
