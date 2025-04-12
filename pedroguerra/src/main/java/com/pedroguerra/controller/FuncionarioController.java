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
            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Empresa> empresas = empresaDAO.listarTodas();
    
            model.addAttribute("funcionario", new Funcionario());
            model.addAttribute("empresas", empresas); // envia as empresas para o formulário
            return "funcionario";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar empresas: " + e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/funcionario/cadastrar")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario,
                                   @RequestParam String tipo,
                                   @RequestParam String empresaCnpj, // novo campo
                                   Model model) {
    try {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.inserir(funcionario);

        // Insere vínculo com empresa
        EmpregaDAO empregaDAO = new EmpregaDAO();
        empregaDAO.inserir(empresaCnpj, funcionario.getMatricula());

        // Insere no tipo específico
        switch (tipo) {
            case "engenheiro":
                new EngenheiroDAO().inserir(funcionario.getMatricula());
                break;
            case "socio":
                new SocioDAO().inserir(funcionario.getMatricula());
                break;
            case "operador":
                new OperadorDroneDAO().inserir(funcionario.getMatricula());
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
    @PostMapping("/funcionario/atualizar")
    public String atualizarFuncionario(@ModelAttribute Funcionario funcionario, Model model) {
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            dao.atualizar(funcionario);
            return "redirect:/funcionario/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao atualizar funcionário: " + e.getMessage());
            return "erro";
        }
    }
}
