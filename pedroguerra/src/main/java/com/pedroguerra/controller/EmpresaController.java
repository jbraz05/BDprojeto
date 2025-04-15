package com.pedroguerra.controller;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Endereco;
import com.pedroguerra.model.LocalizacaoAtuacao;
import com.pedroguerra.service.EmpresaService;
import com.pedroguerra.dao.LocalizacaoAtuacaoDAO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class EmpresaController {

    private final EmpresaService empresaService = new EmpresaService();
    private final LocalizacaoAtuacaoDAO localizacaoDAO = new LocalizacaoAtuacaoDAO();

    @GetMapping("/")
    public String mostrarHome() {
        return "home";
    }

    @GetMapping("/empresa")
    public String mostrarFormularioEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());

        try {
            List<LocalizacaoAtuacao> localizacoes = localizacaoDAO.listarTodas();
            model.addAttribute("localizacoes", localizacoes);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar localizações.");
        }

        return "empresa";
    }

    @PostMapping("/empresa/cadastrar")
    public String cadastrarEmpresa(@ModelAttribute Empresa empresa,
                                   @RequestParam String localizacaoCodigo,
                                   @RequestParam String cep,
                                   @RequestParam String rua,
                                   @RequestParam String numero,
                                   @RequestParam String bairro,
                                   @RequestParam String cidade,
                                   Model model) {
        try {
            Endereco endereco = new Endereco(cep, numero, cidade, bairro, rua);
            empresaService.cadastrarEmpresa(empresa, endereco, localizacaoCodigo);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao cadastrar empresa: " + e.getMessage());
            model.addAttribute("empresa", new Empresa());
            return "empresa";
        }
    }

    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        try {
            List<String[]> empresas = empresaService.listarEmpresasComEnderecoEAtuacao();
            model.addAttribute("empresas", empresas);
            return "lista-empresas";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar empresas: " + e.getMessage());
            return "lista-empresas";
        }
    }

    @PostMapping("/empresa/remover")
    public String removerEmpresa(@RequestParam String cnpj, Model model) {
        try {
            empresaService.removerEmpresa(cnpj);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao remover empresa: " + e.getMessage());
            return "empresa";
        }
    }

    @PostMapping("/empresa/atualizar")
    public String atualizarEmpresa(@ModelAttribute Empresa empresa, Model model) {
        try {
            empresaService.atualizarEmpresa(empresa);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao atualizar empresa: " + e.getMessage());
            return "empresa";
        }
    }

    @GetMapping("/empresa/editar")
    public String mostrarFormularioEdicao(@RequestParam String cnpj, Model model) {
        try {
            Empresa empresa = empresaService.buscarEmpresaPorCnpj(cnpj);
            model.addAttribute("empresa", empresa);

            List<LocalizacaoAtuacao> localizacoes = localizacaoDAO.listarTodas();
            model.addAttribute("localizacoes", localizacoes);

            return "editar-empresa";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao buscar empresa: " + e.getMessage());
            return "empresa";
        }
    }
}
