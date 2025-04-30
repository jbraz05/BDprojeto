package com.pedroguerra.service;

import com.pedroguerra.dao.DashboardDAO;

import java.sql.SQLException;
import java.util.Map;

public class DashboardService {

    private final DashboardDAO dao = new DashboardDAO();

    public Map<String, Integer> buscarTopClientesPorServico() throws SQLException {
        return dao.getTopClientesPorQuantidade();
    }

    public Map<String, Double> buscarServicosMaisLucrativos() throws SQLException {
        return dao.getTopServicosPorValor();
    }

    public Map<String, Integer> buscarStatusServicos() throws SQLException {
        return dao.getStatusServicos();
    }
}
