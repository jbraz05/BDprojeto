package com.pedroguerra.controller;

import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dto.FuncionarioDTO;
import com.pedroguerra.model.Contato;
import com.pedroguerra.service.FuncionarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Comparator;

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
    public String salvarFuncionario(@ModelAttribute("funcionario") FuncionarioDTO dto, Model model) {
        try {
            Contato contato = new Contato("CTF_" + dto.getMatricula(), dto.getTelefone(), dto.getEmail(), dto.getMatricula(), null);
    
            boolean existe = service.buscarFuncionarioDTO(dto.getMatricula()) != null;
    
            if (existe) {
                service.atualizarFuncionario(dto, contato); 
            } else {
                service.salvarFuncionario(dto, contato);
            }
    
            return "redirect:/funcionarios";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao salvar funcionário: " + e.getMessage());
            return "funcionario";
        }
    }
    

    
    @PostMapping("/remover-funcionario")
    public String removerFuncionario(@RequestParam String matricula) {
        try {
            service.removerFuncionario(matricula);
            return "redirect:/funcionarios";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover funcionário", e);
        }
    }

    @GetMapping("/editar-funcionario")
    public String editarFuncionario(@RequestParam String matricula, Model model) {
        try {
            FuncionarioDTO dto = service.buscarFuncionarioDTO(matricula);
            model.addAttribute("funcionario", dto);
            model.addAttribute("telefone", dto.getTelefone());
            model.addAttribute("email", dto.getEmail());
            model.addAttribute("empresas", empresaDAO.listarTodasComLocalizacao());
            model.addAttribute("supervisores", service.listarTodos());
            return "funcionario";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar dados para edição", e);
        }
    }

    @GetMapping("/funcionarios")
public String listarFuncionarios(@RequestParam(required = false) String cnpj,
                                 @RequestParam(required = false) String sort,
                                 Model model) {
    try {
        List<FuncionarioDTO> funcionarios;

        if (cnpj != null && !cnpj.isEmpty()) {
            funcionarios = service.listarFuncionariosPorEmpresaDTO(cnpj);
        } else {
            funcionarios = service.listarTodos();
        }

        if ("nome".equals(sort)) {
            funcionarios.sort(Comparator.comparing(FuncionarioDTO::getNome, String.CASE_INSENSITIVE_ORDER));
        } else if ("salario".equals(sort)) {
            funcionarios.sort(Comparator.comparing(FuncionarioDTO::getSalario));
        }

        model.addAttribute("empresas", empresaDAO.listarTodasComLocalizacao());
        model.addAttribute("funcionarios", funcionarios);
        return "lista-funcionarios";
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Erro ao listar funcionários", e);
    }
}



    @GetMapping("/funcionarios/por-empresa")
@ResponseBody
public List<FuncionarioDTO> buscarFuncionariosPorEmpresa(@RequestParam String cnpj) {
    try {
        return service.listarFuncionariosPorEmpresaDTO(cnpj);
    } catch (Exception e) {
        e.printStackTrace();
        return List.of();
    }
}

}
