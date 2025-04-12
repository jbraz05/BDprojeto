package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class EmpresaController {

    @GetMapping("/")
    public String mostrarHome() {
        return "home";
    }

    // FORMULÁRIO DE CADASTRO DE EMPRESA
    @GetMapping("/empresa")
    public String mostrarFormularioEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());

        try {
            LocalizacaoAtuacaoDAO localizacaoDAO = new LocalizacaoAtuacaoDAO();
            List<LocalizacaoAtuacao> localizacoes = localizacaoDAO.listarTodas();
            model.addAttribute("localizacoes", localizacoes);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar localizações.");
        }

        return "empresa";
    }

    // CADASTRO DA EMPRESA E RELAÇÃO COM A LOCALIZAÇÃO (ATUA)
    @PostMapping("/empresa/cadastrar")
    public String cadastrarEmpresa(@ModelAttribute Empresa empresa,
                                   @RequestParam String localizacaoCodigo,
                                   Model model) {
        try {
            EmpresaDAO empresaDAO = new EmpresaDAO();
            empresaDAO.inserir(empresa);

            AtuaDAO atuaDAO = new AtuaDAO();
            atuaDAO.inserir(empresa.getCnpj(), localizacaoCodigo);

            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao cadastrar empresa: " + e.getMessage());
            return "erro";
        }
    }

    // LISTA TODAS AS EMPRESAS
    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            List<String[]> empresas = dao.listarTodasComLocalizacao();
            model.addAttribute("empresas", empresas);
            return "lista-empresas";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar empresas: " + e.getMessage());
            return "erro";
        }
    }

    // REMOÇÃO POR CNPJ
    @PostMapping("/empresa/remover")
    public String removerEmpresa(@RequestParam String cnpj, Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            dao.removerPorCnpj(cnpj);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao remover empresa: " + e.getMessage());
            return "erro";
        }
    }

    // ATUALIZAÇÃO DE DADOS DA EMPRESA
    @PostMapping("/empresa/atualizar")
    public String atualizarEmpresa(@ModelAttribute Empresa empresa, Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            dao.atualizar(empresa);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao atualizar empresa: " + e.getMessage());
            return "erro";
        }
    }

    // FORMULÁRIO DE EDIÇÃO DA EMPRESA
    @GetMapping("/empresa/editar")
    public String mostrarFormularioEdicao(@RequestParam String cnpj, Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            Empresa empresa = dao.buscarPorCnpj(cnpj);
            model.addAttribute("empresa", empresa);

            // Também carrega as localizações (caso queira editar depois)
            LocalizacaoAtuacaoDAO localizacaoDAO = new LocalizacaoAtuacaoDAO();
            List<LocalizacaoAtuacao> localizacoes = localizacaoDAO.listarTodas();
            model.addAttribute("localizacoes", localizacoes);

            return "editar-empresa";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao buscar empresa: " + e.getMessage());
            return "erro";
        }
    }
    
}
