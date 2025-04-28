package com.pedroguerra.controller;

import com.pedroguerra.dto.FuncionarioDTO;
import com.pedroguerra.dto.ServicoDTO;
import com.pedroguerra.model.RelatorioServico;
import com.pedroguerra.model.Servico;
import com.pedroguerra.service.ClienteService;
import com.pedroguerra.service.EmpresaService;
import com.pedroguerra.service.FuncionarioService;
import com.pedroguerra.service.LocalizacaoService;
import com.pedroguerra.service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ServicoController {

    private final ServicoService servicoService = new ServicoService();
    private final FuncionarioService funcionarioService = new FuncionarioService();
    private final EmpresaService empresaService = new EmpresaService();
    private final LocalizacaoService localizacaoService = new LocalizacaoService();
    private final ClienteService clienteService = new ClienteService();

    @GetMapping("/servico")
    public String exibirFormularioCadastro(Model model) {
        try {
            model.addAttribute("servico", new Servico());
            model.addAttribute("relatorio", new RelatorioServico());
            model.addAttribute("funcionarios", funcionarioService.listarTodos());
            model.addAttribute("operadoresDrone", funcionarioService.listarOperadoresDrone());
            model.addAttribute("empresas", empresaService.listarTodas());
            model.addAttribute("localizacoes", localizacaoService.listarTodas());
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("empresaSelecionada", "");
            model.addAttribute("localizacaoSelecionada", "");
            model.addAttribute("clienteSelecionado", "");
            return "servico";
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar formulário de serviço", e);
        }
    }

   
    @PostMapping("/salvar-servico")
    public String salvarServico(@ModelAttribute("servico") Servico servico,
                                 @RequestParam(required = false) String operadorDroneMatricula,
                                 @RequestParam float area,
                                 @RequestParam("dataRelatorio") Date dataRelatorio,
                                 @RequestParam String cnpjEmpresa,
                                 @RequestParam String codigoLocalizacao,
                                 @RequestParam String cnpjCpfCliente,
                                 @RequestParam String observacoes,
                                 Model model) {
        try {
            boolean existe = servicoService.servicoExiste(servico.getId());
    
            if (existe) {
                throw new RuntimeException("Já existe um serviço com esse ID. Por favor, escolha outro.");
            }
    
            servicoService.salvarServico(servico);
            servicoService.salvarRelatorio(servico.getId(), area, dataRelatorio, observacoes);
            servicoService.vincularClienteAoServico(servico.getId(), cnpjCpfCliente);
    
            if ("voo panorâmico".equalsIgnoreCase(servico.getTipo())) {
                if (operadorDroneMatricula == null || operadorDroneMatricula.isEmpty()) {
                    throw new RuntimeException("Operador de drone é obrigatório para voo panorâmico.");
                }
                servicoService.salvarVooPanoramico(servico.getId(), operadorDroneMatricula);
            } else if ("mapeamento tradicional".equalsIgnoreCase(servico.getTipo())) {
                servicoService.salvarMapeamentoTradicional(servico.getId());
            }
    
            servicoService.vincularEmpresaLocalizacaoAoServico(servico.getId(), cnpjEmpresa, codigoLocalizacao);
    
            return "redirect:/servicos";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erroMensagem", "Erro ao salvar o serviço: " + e.getMessage());
    
            // Limpa o ID para permitir o usuário corrigir
            servico.setId(null);
    
            try {
                model.addAttribute("funcionarios", funcionarioService.listarTodos());
                model.addAttribute("operadoresDrone", funcionarioService.listarOperadoresDrone());
                model.addAttribute("empresas", empresaService.listarTodas());
                model.addAttribute("localizacoes", localizacaoService.listarTodas());
                model.addAttribute("clientes", clienteService.listarTodos());
            } catch (SQLException ex) {
                ex.printStackTrace();
                model.addAttribute("erroMensagem", "Erro ao salvar serviço e ao carregar dados: " + ex.getMessage());
            }
    
            model.addAttribute("empresaSelecionada", cnpjEmpresa);
            model.addAttribute("localizacaoSelecionada", codigoLocalizacao);
            model.addAttribute("clienteSelecionado", cnpjCpfCliente);
            model.addAttribute("relatorio", new RelatorioServico());
    
            return "servico";
        }
    }
    
    


    @GetMapping("/editar-servico")
    public String editarServico(@RequestParam String id, Model model) {
        try {
            ServicoDTO dto = servicoService.buscarServicoDTO(id);
            if (dto == null) throw new RuntimeException("Serviço não encontrado.");
            model.addAttribute("servico", dto);
            model.addAttribute("relatorio",dto );
            model.addAttribute("funcionarios", funcionarioService.listarTodos());
            model.addAttribute("operadoresDrone", funcionarioService.listarOperadoresDrone());
            model.addAttribute("empresas", empresaService.listarTodas());
            model.addAttribute("localizacoes", localizacaoService.listarTodas());
            model.addAttribute("clientes", clienteService.listarTodos());

            String[] dadosPossui = servicoService.buscarEmpresaEAtuacaoDoServico(id);
            model.addAttribute("empresaSelecionada", dadosPossui != null ? dadosPossui[0] : "");
            model.addAttribute("localizacaoSelecionada", dadosPossui != null ? dadosPossui[1] : "");

            String clienteSelecionado = servicoService.buscarClienteDoServico(id);
            model.addAttribute("clienteSelecionado", clienteSelecionado);

            return "servico";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar serviço para edição", e);
        }
    }

    @PostMapping("/remover-servico")
    public String removerServico(@RequestParam String id) {
        try {
            servicoService.removerClienteDoServico(id);
            servicoService.removerVinculoPossui(id);
            servicoService.removerVooPanoramico(id);
            servicoService.removerMapeamentoTradicional(id);
            servicoService.removerRelatorio(id);
            servicoService.removerServico(id);
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

    @GetMapping("/servico/relatorio")
    public String verRelatorio(@RequestParam String id, Model model) {
        try {
            ServicoDTO dto = servicoService.buscarServicoDTO(id);
            if (dto == null) throw new RuntimeException("Serviço não encontrado.");
            model.addAttribute("servico", dto);
            return "relatorio-servico";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar o relatório.");
            return "redirect:/servicos";
        }
    }

@GetMapping("/funcionarios-por-empresa")
@ResponseBody
public List<FuncionarioDTO> buscarFuncionariosPorEmpresa(@RequestParam String cnpj) throws SQLException {
    return funcionarioService.listarFuncionariosPorEmpresaDTO(cnpj);
}

}