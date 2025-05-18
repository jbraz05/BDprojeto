package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardInterativoProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard-interativo-procedure")
public class DashboardInterativoProcedureController {

    private final DashboardInterativoProcedureService service = new DashboardInterativoProcedureService();

    // GET: Renderiza a tela inicial com dados vazios
    @GetMapping
    public String abrirTela(Model model) {
        model.addAttribute("dados", new HashMap<>()); // previne erro de template
        model.addAttribute("tipoGrafico", "bar");
        model.addAttribute("tituloGrafico", "Dashboard Interativo");
        return "dashboard-interativo-procedure";
    }

    // POST tradicional (formulário): gera gráfico fixo
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
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao gerar gráfico: " + e.getMessage());
        }

        return "dashboard-interativo-procedure";
    }

    // NOVO: Suporte a requisição AJAX para geração dinâmica dos gráficos
    @PostMapping("/dados")
    @ResponseBody
    public Map<String, Number> consultarDadosAjax(@RequestBody Map<String, String> payload) {
        String entidade = payload.get("entidade");
        String metrica = payload.get("metrica");

        try {
            return service.obterDados(entidade, metrica);
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
