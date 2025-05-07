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

    public Map<String, Number> getQuantidadeServicosPorFuncionario() throws SQLException {
        String sql = """
            SELECT f.nome, COUNT(s.id) AS total
            FROM Funcionario f
            JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
            GROUP BY f.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getValorTotalServicosPorFuncionario() throws SQLException {
        String sql = """
            SELECT f.nome, SUM(s.valor_medicao) AS total
            FROM Funcionario f
            JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
            GROUP BY f.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }

    public Map<String, Number> getAreaTotalPorEmpresa() throws SQLException {
        String sql = """
            SELECT e.nome, SUM(rs.area) AS total
            FROM Empresa e
            JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
            JOIN Servico s ON s.id = p.fk_servico_id
            JOIN RelatorioServico rs ON s.id = rs.fk_servico_id
            GROUP BY e.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getAreaTotalPorCliente() throws SQLException {
        String sql = """
            SELECT c.nome, SUM(rs.area) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON ct.nota_fiscal = s.id
            JOIN RelatorioServico rs ON s.id = rs.fk_servico_id
            GROUP BY c.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getAreaTotalPorFuncionario() throws SQLException {
        String sql = """
            SELECT f.nome, SUM(rs.area) AS total
            FROM Funcionario f
            JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
            JOIN RelatorioServico rs ON s.id = rs.fk_servico_id
            GROUP BY f.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getServicosConcluidosPorEmpresa() throws SQLException {
        String sql = """
            SELECT e.nome, COUNT(s.id) AS total
            FROM Empresa e
            JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
            JOIN Servico s ON p.fk_servico_id = s.id
            WHERE s.feito = true
            GROUP BY e.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getServicosConcluidosPorCliente() throws SQLException {
        String sql = """
            SELECT c.nome, COUNT(s.id) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON ct.nota_fiscal = s.id
            WHERE s.feito = true
            GROUP BY c.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getServicosConcluidosPorFuncionario() throws SQLException {
        String sql = """
            SELECT f.nome, COUNT(s.id) AS total
            FROM Funcionario f
            JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
            WHERE s.feito = true
            GROUP BY f.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getTempoMedioTotalPorEmpresa() throws SQLException {
        String sql = """
            SELECT e.nome, AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
            FROM Empresa e
            JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
            JOIN Servico s ON p.fk_servico_id = s.id
            GROUP BY e.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getTempoMedioTotalPorCliente() throws SQLException {
        String sql = """
            SELECT c.nome, AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON ct.nota_fiscal = s.id
            GROUP BY c.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
    public Map<String, Number> getTempoMedioTotalPorFuncionario() throws SQLException {
        String sql = """
            SELECT f.nome, AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
            FROM Funcionario f
            JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
            GROUP BY f.nome
            ORDER BY total DESC
        """;
        return executarConsultaSimples(sql);
    }
    
}
