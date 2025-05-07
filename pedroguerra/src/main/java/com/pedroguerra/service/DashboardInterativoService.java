package com.pedroguerra.service;

import com.pedroguerra.dao.DashboardInterativoDAO;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DashboardInterativoService {

    public Map<String, Number> obterDados(String entidade, String metrica) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DashboardInterativoDAO dao = new DashboardInterativoDAO(connection);

            if ("empresa".equals(entidade)) {
                return switch (metrica) {
                    case "quantidade" -> dao.getQuantidadeServicosPorEmpresa();
                    case "valor" -> dao.getValorTotalServicosPorEmpresa();
                    case "area" -> dao.getAreaTotalPorEmpresa();
                    case "concluidos" -> dao.getServicosConcluidosPorEmpresa();
                    case "tempo" -> dao.getTempoMedioTotalPorEmpresa();
                    default -> Map.of();
                };
            } else if ("cliente".equals(entidade)) {
                return switch (metrica) {
                    case "quantidade" -> dao.getQuantidadeServicosPorCliente();
                    case "valor" -> dao.getValorTotalServicosPorCliente();
                    case "area" -> dao.getAreaTotalPorCliente();
                    case "concluidos" -> dao.getServicosConcluidosPorCliente();
                    case "tempo" -> dao.getTempoMedioTotalPorCliente();
                    default -> Map.of();
                };
            } else if ("funcionario".equals(entidade)) {
                return switch (metrica) {
                    case "quantidade" -> dao.getQuantidadeServicosPorFuncionario();
                    case "valor" -> dao.getValorTotalServicosPorFuncionario();
                    case "area" -> dao.getAreaTotalPorFuncionario();
                    case "concluidos" -> dao.getServicosConcluidosPorFuncionario();
                    case "tempo" -> dao.getTempoMedioTotalPorFuncionario();
                    default -> Map.of();
                };
            }

            return Map.of();
        }
    }

}
