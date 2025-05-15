package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardInterativoProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping("/dashboard-interativo-procedure")
public class DashboardInterativoProcedureController {

    private final DashboardInterativoProcedureService service = new DashboardInterativoProcedureService();

    // Suporte para GET: renderiza a tela inicial
    @GetMapping
    public String abrirTela() {
        return "dashboard-interativo-procedure";
    }

    // Suporte para POST: gera o gráfico com base no formulário
    @PostMapping
    public String gerarGrafico(
            @RequestParam("entidade") String entidade,
            @RequestParam("metrica") String metrica,
            @RequestParam("tipoGrafico") String tipoGrafico,
            Model model) {

        try {
            Map<String, Number> dados = service.obterDados(entidade, metrica);
            String tituloGrafico = "Gráfico de " + metrica + " por " + entidade;

            model.addAttribute("dados", dados);
            model.addAttribute("tipoGrafico", tipoGrafico);
            model.addAttribute("tituloGrafico", tituloGrafico);

        } catch (SQLException e) {
            e.printStackTrace(); // ou log de erro
            model.addAttribute("erro", "Erro ao gerar gráfico: " + e.getMessage());
        }

        return "dashboard-interativo-procedure";
    }
}
