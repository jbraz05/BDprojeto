package com.pedroguerra.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardInterativoProcedureDAO {

    private final Connection connection;

    public DashboardInterativoProcedureDAO(Connection connection) {
        this.connection = connection;
    }

    private Map<String, BigDecimal> executarProcedure(String nomeProcedure) throws SQLException {
        Map<String, BigDecimal> resultado = new LinkedHashMap<>();

        try (CallableStatement stmt = connection.prepareCall("{CALL " + nomeProcedure + "()}");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getBigDecimal("total"));
            }
        }

        return resultado;
    }

    public Map<String, BigDecimal> getValorTotalPorEntidade(String entidade) throws SQLException {
        return switch (entidade.toLowerCase()) {
            case "empresa" -> executarProcedure("proc_valor_total_por_empresa");
            case "cliente" -> executarProcedure("proc_valor_total_por_cliente");
            case "funcionario" -> executarProcedure("proc_valor_total_por_funcionario");
            default -> Map.of(); // vazio
        };
    }

    public Map<String, BigDecimal> getQuantidadeServicosPorEntidade(String entidade) throws SQLException {
        return switch (entidade.toLowerCase()) {
            case "empresa" -> executarProcedure("proc_quantidade_servicos_por_empresa");
            case "cliente" -> executarProcedure("proc_quantidade_servicos_por_cliente");
            case "funcionario" -> executarProcedure("proc_quantidade_servicos_por_funcionario");
            default -> Map.of();
        };
    }

    public Map<String, BigDecimal> getAreaTotalPorEntidade(String entidade) throws SQLException {
        return switch (entidade.toLowerCase()) {
            case "empresa" -> executarProcedure("proc_area_total_por_empresa");
            case "cliente" -> executarProcedure("proc_area_total_por_cliente");
            case "funcionario" -> executarProcedure("proc_area_total_por_funcionario");
            default -> Map.of();
        };
    }

    public Map<String, BigDecimal> getServicosConcluidosPorEntidade(String entidade) throws SQLException {
        return switch (entidade.toLowerCase()) {
            case "empresa" -> executarProcedure("proc_servicos_concluidos_por_empresa");
            case "cliente" -> executarProcedure("proc_servicos_concluidos_por_cliente");
            case "funcionario" -> executarProcedure("proc_servicos_concluidos_por_funcionario");
            default -> Map.of();
        };
    }

    public Map<String, BigDecimal> getTempoMedioTotalPorEntidade(String entidade) throws SQLException {
        return switch (entidade.toLowerCase()) {
            case "empresa" -> executarProcedure("proc_tempo_medio_total_por_empresa");
            case "cliente" -> executarProcedure("proc_tempo_medio_total_por_cliente");
            case "funcionario" -> executarProcedure("proc_tempo_medio_total_por_funcionario");
            default -> Map.of();
        };
    }
}
