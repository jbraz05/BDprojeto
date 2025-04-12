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

    @GetMapping("/empresa")
    public String mostrarEmpresa() {
        return "empresa";
    }

    @PostMapping("/empresa/cadastrar")
    @ResponseBody
    public String cadastrarEmpresa(@RequestBody Empresa empresa) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            dao.inserir(empresa);
            return "Empresa cadastrada com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar empresa: " + e.getMessage();
        }
    }

    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            List<Empresa> empresas = dao.listarTodas();
            model.addAttribute("empresas", empresas);
            return "lista-empresas";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar empresas: " + e.getMessage());
            return "erro";
        }
    }


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
    
@GetMapping("/empresa/editar")
public String mostrarFormularioEdicao(@RequestParam String cnpj, Model model) {
    try {
        EmpresaDAO dao = new EmpresaDAO();
        Empresa empresa = dao.buscarPorCnpj(cnpj);
        model.addAttribute("empresa", empresa);
        return "editar-empresa"; 
    } catch (SQLException e) {
        e.printStackTrace();
        model.addAttribute("erro", "Erro ao buscar empresa: " + e.getMessage());
        return "erro";
    }
}
}