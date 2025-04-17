package com.pedroguerra.controller;

import com.pedroguerra.dto.ServicoDTO;
import com.pedroguerra.model.Servico;
import com.pedroguerra.service.ServicoService;
import com.pedroguerra.service.FuncionarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ServicoController {

    private final ServicoService servicoService = new ServicoService();
    private final FuncionarioService funcionarioService = new FuncionarioService();

    @GetMapping("/servico")
    public String exibirFormularioCadastro(Model model) {
        try {
            model.addAttribute("servico", new Servico());
            model.addAttribute("funcionarios", funcionarioService.listarTodos());
            model.addAttribute("operadoresDrone", funcionarioService.listarOperadoresDrone());
            return "servico";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar formulário de serviço", e);
        }
    }

    @PostMapping("/salvar-servico")
    public String salvarServico(@ModelAttribute("servico") Servico servico,
                                @RequestParam(required = false) String operadorDroneMatricula) {
        try {
            boolean existe = servicoService.servicoExiste(servico.getId());

            if (existe) {
                servicoService.atualizarServico(servico);
            } else {
                servicoService.salvarServico(servico);
            }

            if ("voo panorâmico".equalsIgnoreCase(servico.getTipo())) {
                if (operadorDroneMatricula == null || operadorDroneMatricula.isEmpty()) {
                    throw new RuntimeException("Operador de drone é obrigatório para voo panorâmico.");
                }
                servicoService.salvarVooPanoramico(servico.getId(), operadorDroneMatricula);
            } else if ("mapeamento tradicional".equalsIgnoreCase(servico.getTipo())) {
                servicoService.salvarMapeamentoTradicional(servico.getId());
            }

            return "redirect:/servicos";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar serviço", e);
        }
    }

    @GetMapping("/editar-servico")
    public String editarServico(@RequestParam String id, Model model) {
        try {
            ServicoDTO dto = servicoService.buscarServicoDTO(id);
            if (dto == null) throw new RuntimeException("Serviço não encontrado.");

            Servico servico = new Servico(dto.getId(), dto.getData(), dto.getTipo(), dto.getFkFuncionarioMatricula());
            model.addAttribute("servico", servico);
            model.addAttribute("funcionarios", funcionarioService.listarTodos());
            model.addAttribute("operadoresDrone", funcionarioService.listarOperadoresDrone());
            return "servico";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar serviço para edição", e);
        }
    }

    @GetMapping("/remover-servico")
    public String removerServico(@RequestParam String id) {
        try {
            servicoService.removerServico(id); // remover também de tabelas filhas (idealmente)
            return "redirect:/servicos";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover serviço", e);
        }
    }

    @GetMapping("/servicos")
    public String listarServicos(Model model) {
        try {
            List<ServicoDTO> servicos = servicoService.listarTodosDTO();
            model.addAttribute("servicos", servicos);
            return "lista-servicos";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar serviços", e);
        }
    }
}
