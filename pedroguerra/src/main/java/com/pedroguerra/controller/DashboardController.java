package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
