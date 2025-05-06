package com.pedroguerra.service;

import com.pedroguerra.dao.DashboardInterativoDAO;
import com.pedroguerra.config.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DashboardInterativoService {

    public Map<String, Number> obterDados(String entidade, String metrica) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DashboardInterativoDAO dao = new DashboardInterativoDAO(connection);
    
            if ("empresa".equals(entidade)) {
                return "quantidade".equals(metrica)
                    ? dao.getQuantidadeServicosPorEmpresa()
                    : dao.getValorTotalServicosPorEmpresa();
            } else if ("cliente".equals(entidade)) {
                return "quantidade".equals(metrica)
                    ? dao.getQuantidadeServicosPorCliente()
                    : dao.getValorTotalServicosPorCliente();
            }
            return Map.of();
        }
    }
    
}
