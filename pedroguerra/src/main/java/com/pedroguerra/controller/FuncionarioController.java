package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.util.List;

@Controller
public class FuncionarioController {

    @GetMapping("/funcionario")
    public String mostrarFormularioFuncionario(Model model) {
        try {
            model.addAttribute("funcionario", new Funcionario());
            return "funcionario";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar formulário: " + e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/funcionario/cadastrar")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario, Model model) {
        try {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.inserir(funcionario);
            return "redirect:/funcionario/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao cadastrar funcionário: " + e.getMessage());
            return "erro";
        }
    }

    @GetMapping("/funcionario/listar")
    public String listarFuncionarios(Model model) {
        try {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
            model.addAttribute("funcionarios", funcionarios);
            return "lista-funcionario";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar funcionários: " + e.getMessage());
            return "erro";
        }
    }
}