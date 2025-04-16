package com.pedroguerra.controller;

import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dto.FuncionarioDTO;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.service.FuncionarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FuncionarioController {

    private final FuncionarioService service = new FuncionarioService();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @GetMapping("/funcionario")
    public String exibirFormularioCadastro(Model model) {
        try {
            model.addAttribute("empresas", empresaDAO.listarTodasComLocalizacao());
            model.addAttribute("supervisores", service.listarTodos());
            model.addAttribute("funcionario", new FuncionarioDTO());
            return "funcionario";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar formulário de cadastro", e);
        }
    }

    @PostMapping("/salvar-funcionario")
    public String salvarFuncionario(@ModelAttribute("funcionario") FuncionarioDTO dto) {
        try {
            service.salvarFuncionario(dto);
            return "redirect:/funcionarios";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar funcionário", e);
        }
    }

    @GetMapping("/funcionarios")
    public String listarFuncionarios(@RequestParam(required = false) String cnpj, Model model) {
        try {
            List<Funcionario> funcionarios = (cnpj != null && !cnpj.isEmpty())
                    ? service.listarFuncionariosPorEmpresa(cnpj)
                    : service.listarTodos();

            model.addAttribute("empresas", empresaDAO.listarTodasComLocalizacao());
            model.addAttribute("funcionarios", funcionarios);
            return "lista-funcionarios";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar funcionários", e);
        }
    }

    @GetMapping("/remover-funcionario")
    public String removerFuncionario(@RequestParam String matricula) {
        try {
            service.removerFuncionario(matricula);
            return "redirect:/funcionarios";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover funcionário", e);
        }
    }
}
