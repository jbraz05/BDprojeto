package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.Year;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class DashboardController {

    private final DashboardService dashboardService = new DashboardService();

    @GetMapping("/dashboards")
    public String exibirDashboards() {
        return "dashboards";
    }

    @GetMapping("/dashboards/clientes-top")
    public String clientesComMaisServicos(Model model) throws SQLException {
        Map<String, Integer> dados = dashboardService.buscarTopClientesPorServico();
        model.addAttribute("clientes", dados);
        return "grafico-clientes";
    }

    @GetMapping("/dashboards/servicos-mais-caros")
    public String servicosMaisCaros(Model model) throws SQLException {
        Map<String, Double> dados = dashboardService.buscarServicosMaisLucrativos();
        model.addAttribute("valores", dados);
        return "grafico-valores";
    }

    @GetMapping("/dashboards/servicos-status")
    public String servicosStatus(Model model) throws SQLException {
        Map<String, Integer> status = dashboardService.buscarStatusServicos();
        model.addAttribute("status", status);
        return "grafico-status";
    }

    @GetMapping("/dashboards/receita-mensal")
    public String receitaMensal(Model model) throws SQLException {
        int anoAtual = Year.now().getValue();
        Map<String, BigDecimal> receita = dashboardService.buscarReceitaMensal(anoAtual);
        model.addAttribute("receita", receita);
        model.addAttribute("ano", anoAtual);
        return "grafico-receita";
    }

    @GetMapping("/dashboards/clientes-receita")
    public String topClientesReceita(Model model) throws SQLException {
        int anoAtual = Year.now().getValue();
        Map<String, BigDecimal> topClientes = dashboardService.buscarTopClientesPorReceita(anoAtual);
        model.addAttribute("topClientes", topClientes);
        model.addAttribute("ano", anoAtual);
        return "grafico-clientes-receita";
    }
}
