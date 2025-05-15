package com.pedroguerra.service;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.DashboardInterativoProcedureDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardInterativoProcedureService {

    public Map<String, Number> obterDados(String entidade, String metrica) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DashboardInterativoProcedureDAO dao = new DashboardInterativoProcedureDAO(connection);

            Map<String, BigDecimal> dados;

            switch (metrica.toLowerCase()) {
                case "valor":
                    dados = dao.getValorTotalPorEntidade(entidade);
                    break;
                case "quantidade":
                    dados = dao.getQuantidadeServicosPorEntidade(entidade);
                    break;
                case "area":
                    dados = dao.getAreaTotalPorEntidade(entidade);
                    break;
                case "concluidos":
                    dados = dao.getServicosConcluidosPorEntidade(entidade);
                    break;
                case "tempo":
                    dados = dao.getTempoMedioTotalPorEntidade(entidade);
                    break;
                default:
                    return Map.of();
            }

            // Converter para Map<String, Number>
            Map<String, Number> resultado = new HashMap<>();
            for (Map.Entry<String, BigDecimal> entry : dados.entrySet()) {
                resultado.put(entry.getKey(), entry.getValue());
            }

            return resultado;
        }
    }
}
