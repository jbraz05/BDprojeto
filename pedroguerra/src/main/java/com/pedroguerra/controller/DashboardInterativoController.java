package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardInterativoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/dashboard-interativo")
public class DashboardInterativoController {

    private final DashboardInterativoService dashboardService = new DashboardInterativoService();

    @GetMapping
    public String mostrarFormulario() {
        return "dashboard-interativo";
    }

    @PostMapping
    public String gerarGrafico(@RequestParam("entidade") String entidade, Model model) throws SQLException {
        model.addAttribute("tipoSelecionado", entidade);
        model.addAttribute("dados", dashboardService.obterDados(entidade));
        return "dashboard-interativo";
    }
}
