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
        } else if ("empresa".equals(entidade) && "valor".equals(metrica)) {
            titulo = "Valor Total das Medições por Empresa";
        } else if ("empresa".equals(entidade) && "area".equals(metrica)) {
            titulo = "Área Total dos Serviços por Empresa (ha)";
        } else if ("empresa".equals(entidade) && "concluidos".equals(metrica)) {
            titulo = "Quantidade de Serviços Concluídos por Empresa";
        } else if ("empresa".equals(entidade) && "tempo".equals(metrica)) {
            titulo = "Tempo Médio Total por Serviço por Empresa (dias)";
        } else if ("cliente".equals(entidade) && "quantidade".equals(metrica)) {
            titulo = "Quantidade de Serviços por Cliente";
        } else if ("cliente".equals(entidade) && "valor".equals(metrica)) {
            titulo = "Valor Total das Medições por Cliente";
        } else if ("cliente".equals(entidade) && "area".equals(metrica)) {
            titulo = "Área Total dos Serviços por Cliente (ha)";
        } else if ("cliente".equals(entidade) && "concluidos".equals(metrica)) {
            titulo = "Quantidade de Serviços Concluídos por Cliente";
        } else if ("cliente".equals(entidade) && "tempo".equals(metrica)) {
            titulo = "Tempo Médio Total por Serviço por Cliente (dias)";
        } else if ("funcionario".equals(entidade) && "quantidade".equals(metrica)) {
            titulo = "Quantidade de Serviços por Funcionário";
        } else if ("funcionario".equals(entidade) && "valor".equals(metrica)) {
            titulo = "Valor Total das Medições por Funcionário";
        } else if ("funcionario".equals(entidade) && "area".equals(metrica)) {
            titulo = "Área Total dos Serviços por Funcionário (ha)";
        } else if ("funcionario".equals(entidade) && "concluidos".equals(metrica)) {
            titulo = "Quantidade de Serviços Concluídos por Funcionário";
        } else if ("funcionario".equals(entidade) && "tempo".equals(metrica)) {
            titulo = "Tempo Médio Total por Serviço por Funcionário (dias)";
        }

        model.addAttribute("tituloGrafico", titulo);

        return "dashboard-interativo";
    }

}
