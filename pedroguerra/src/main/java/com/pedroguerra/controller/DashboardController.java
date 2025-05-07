package com.pedroguerra.controller;

import com.pedroguerra.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.Year;
import java.sql.SQLException;
import java.util.List;
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
    public String topClientesReceita(
            @RequestParam(value = "ano", required = false) Integer anoParam,
            Model model
    ) throws SQLException {
        int anoSelecionado = (anoParam != null) ? anoParam : Year.now().getValue();

        // Pega lista dinâmica de anos
        List<Integer> anosDisponiveis = dashboardService.buscarAnosComServico();

        Map<String, BigDecimal> topClientes = dashboardService.buscarTopClientesPorReceita(anoSelecionado);
        model.addAttribute("topClientes", topClientes);
        model.addAttribute("anoSelecionado", anoSelecionado);
        model.addAttribute("anosDisponiveis", anosDisponiveis);
        return "grafico-clientes-receita";
    }

    @GetMapping("/dashboards/servicos-tipo")
    public String servicosPorTipo(Model model) throws SQLException {
        Map<String, Integer> distrib = dashboardService.buscarDistribuicaoServicosPorTipo();
        model.addAttribute("distribuicao", distrib);
        return "grafico-tipos";
    }

    @GetMapping("/dashboards/salario-funcao")
    public String salarioPorFuncao(Model model) throws SQLException {
        Map<String, BigDecimal> mediaSalario = dashboardService.buscarMediaSalarioPorFuncao();
        model.addAttribute("mediaSalario", mediaSalario);
        return "grafico-salario-funcao";
    }

    @GetMapping("/dashboards/ceps-mais-usados")
    public String cepsMaisUsados(Model model) throws SQLException {
    Map<String, Integer> dados = dashboardService.buscarCepsMaisUsadosEmServicos();
    model.addAttribute("ceps", dados);
    return "grafico-ceps";
}

@GetMapping("/dashboards/receita-localizacao")
public String receitaPorLocalizacao(Model model) throws SQLException {
    Map<String, BigDecimal> dados = dashboardService.buscarReceitaPorLocalizacao();
    model.addAttribute("dados", dados);
    return "grafico-localizacao";
}


}
