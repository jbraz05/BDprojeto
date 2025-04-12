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
        model.addAttribute("funcionario", new Funcionario());
        return "funcionario";
    }

    @PostMapping("/funcionario/cadastrar")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario,
                                    @RequestParam String tipo, Model model) {
        try {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.inserir(funcionario); //insere na tabela Funcionario

            // Agora insere na tabela correspondente ao tipo:
            switch (tipo) {
                case "engenheiro":
                    EngenheiroDAO engenheiroDAO = new EngenheiroDAO();
                    engenheiroDAO.inserir(funcionario.getMatricula());
                    break;
                case "socio":
                    SocioDAO socioDAO = new SocioDAO();
                    socioDAO.inserir(funcionario.getMatricula());
                    break;
                case "operador":
                    OperadorDroneDAO operadorDAO = new OperadorDroneDAO();
                    operadorDAO.inserir(funcionario.getMatricula());
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de funcionário inválido.");
            }

            return "redirect:/funcionario/listar";

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao cadastrar funcionário: " + e.getMessage());
            return "erro";
        }
    }


    @GetMapping("/funcionario/listar")
    public String listarFuncionarios(Model model) {
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            List<Funcionario> funcionarios = dao.listarTodos();
            model.addAttribute("funcionarios", funcionarios);
            return "lista-funcionario";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar funcionários: " + e.getMessage());
            return "erro";
        }
    }
    @PostMapping("/funcionario/remover")
    public String removerFuncionario(@RequestParam String matricula, Model model) {
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            dao.removerPorMatricula(matricula);
            return "redirect:/funcionario/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao remover funcionário: " + e.getMessage());
            return "erro";
        }
    }
}
