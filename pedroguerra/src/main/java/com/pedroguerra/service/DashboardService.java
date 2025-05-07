package com.pedroguerra.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pedroguerra.dao.DashboardDAO;

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

    public Map<String, BigDecimal> buscarReceitaMensal(int ano) throws SQLException {
        return dao.getReceitaMensal(ano);
    }

    public Map<String, BigDecimal> buscarTopClientesPorReceita(int ano) throws SQLException {
        return dao.getTopClientesPorReceita(ano);
    }

    public Map<String, Integer> buscarDistribuicaoServicosPorTipo() throws SQLException {
        return dao.getDistribuicaoServicosPorTipo();
    }

    public Map<String, BigDecimal> buscarMediaSalarioPorFuncao() throws SQLException {
        return dao.getMediaSalarioPorFuncao();
    }

    public Map<String, Integer> buscarCepsMaisUsadosEmServicos() throws SQLException {
        return dao.getCepsMaisUsadosEmServicos();
    }
    
    public List<Integer> buscarAnosComServico() throws SQLException {
        return dao.getAnosComServico();
    }

    public Map<String, BigDecimal> buscarReceitaPorLocalizacao() throws SQLException {
        return dao.getReceitaPorLocalizacao();
    }
}
