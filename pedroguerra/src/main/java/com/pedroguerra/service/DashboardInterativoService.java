package com.pedroguerra.service;

import com.pedroguerra.dao.DashboardInterativoDAO;
import com.pedroguerra.config.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DashboardInterativoService {

    public Map<String, BigDecimal> obterDados(String tipo) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DashboardInterativoDAO dao = new DashboardInterativoDAO(connection);
            if ("empresa".equalsIgnoreCase(tipo)) {
                return dao.getDadosPorEmpresa();
            } else if ("cliente".equalsIgnoreCase(tipo)) {
                return dao.getDadosPorCliente();
            }
            return Map.of();
        }
    }
}
