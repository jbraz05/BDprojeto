package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ServicoController {

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
        return "editar-empresa"; // Nome do HTML que vamos criar
    } catch (SQLException e) {
        e.printStackTrace();
        model.addAttribute("erro", "Erro ao buscar empresa: " + e.getMessage());
        return "erro";
    }
}



@GetMapping("/funcionario")
public String mostrarFormularioFuncionario(Model model) {
    model.addAttribute("funcionario", new Funcionario());
    return "funcionario"; // Thymeleaf vai procurar por funcionario.html em templates
}

@PostMapping("/funcionario/cadastrar")
public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario,
                                   @RequestParam String tipo, Model model) {
    try {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.inserir(funcionario); // insere na tabela Funcionario

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
        List<Funcionario> funcionarios = dao.listarTodos(); // precisa criar esse método!
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