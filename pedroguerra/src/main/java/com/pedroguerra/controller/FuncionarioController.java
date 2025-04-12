package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @GetMapping("/empresa/{cnpj}/funcionarios")
    public String listarFuncionarios(@PathVariable String cnpj, Model model) {
    try {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = funcionarioDAO.listarPorEmpresa(cnpj);

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.buscarPorCnpj(cnpj);

        // Mapeia cada matrícula para a empresa correspondente (usando a tabela Emprega)
        Map<String, Empresa> empresasPorFuncionario = new HashMap<>();
        for (Funcionario f : funcionarios) {
            Empresa emp = empresaDAO.buscarPorFuncionario(f.getMatricula());
            empresasPorFuncionario.put(f.getMatricula(), emp);
        }

        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("empresa", empresa);
        model.addAttribute("empresasPorFuncionario", empresasPorFuncionario);
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
