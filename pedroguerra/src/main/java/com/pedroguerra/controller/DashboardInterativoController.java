package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardInterativoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping("/dashboard-interativo")
public class DashboardInterativoController {

    private final DashboardInterativoService dashboardService = new DashboardInterativoService();

    @GetMapping
    public String mostrarFormulario() {
        return "dashboard-interativo";
    }

   @PostMapping
public String gerarGrafico(@RequestParam("entidade") String entidade,
                           @RequestParam("metrica") String metrica,
                           Model model) throws SQLException {

    Map<String, Number> dados = dashboardService.obterDados(entidade, metrica);
    model.addAttribute("dados", dados);
    model.addAttribute("tipoSelecionado", entidade);

    String titulo = "";
    if ("empresa".equals(entidade) && "quantidade".equals(metrica)) {
        titulo = "Quantidade de Serviços por Empresa";
    } else if ("empresa".equals(entidade)) {
        titulo = "Valor Total das Medições por Empresa";
    } else if ("cliente".equals(entidade) && "quantidade".equals(metrica)) {
        titulo = "Quantidade de Serviços por Cliente";
    } else {
        titulo = "Valor Total das Medições por Cliente";
    }

    model.addAttribute("tituloGrafico", titulo);

    return "dashboard-interativo";
}

}
