package com.pedroguerra.controller;

import com.pedroguerra.dao.EnderecoDAO;
import com.pedroguerra.dto.ClienteDTO;
import com.pedroguerra.model.Cliente;
import com.pedroguerra.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClienteController {

    private final ClienteService service = new ClienteService();
    private final EnderecoDAO enderecoDAO = new EnderecoDAO();

    @GetMapping("/cliente")
    public String exibirFormularioCadastro(Model model) {
        try {
            model.addAttribute("cliente", new ClienteDTO());
            return "cadastro-cliente";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar formulário de cliente", e);
        }
    }

    @PostMapping("/salvar-cliente")
    public String salvarCliente(@ModelAttribute("cliente") ClienteDTO dto) {
        try {
            if (service.clienteExiste(dto.getCnpjCpf())) {
                service.atualizarCliente(dto); // Agora passa DTO diretamente
            } else {
                service.salvarCliente(dto);
            }
            return "redirect:/clientes";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }

    @GetMapping("/editar-cliente")
    public String editarCliente(@RequestParam String cpf, Model model) {
        try {
            Cliente cliente = service.buscarClientePorCnpjCpf(cpf);
            if (cliente == null) return "redirect:/clientes";

            ClienteDTO dto = new ClienteDTO();
            dto.setCnpjCpf(cliente.getCnpjCpf());
            dto.setNome(cliente.getNome());
            dto.setFkEnderecoCep(cliente.getFkEnderecoCep());
            
            var endereco = enderecoDAO.buscarPorCep(cliente.getFkEnderecoCep());
            if (endereco != null) {
                dto.setRuaEndereco(endereco.getRua());
                dto.setNumeroEndereco(endereco.getNumero());
                dto.setBairroEndereco(endereco.getBairro());
                dto.setCidadeEndereco(endereco.getCidade());
            }
    
            model.addAttribute("cliente", dto);
            return "cadastro-cliente";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar cliente para edição", e);
        }
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        try {
            List<ClienteDTO> clientes = service.listarTodos(); 
            model.addAttribute("clientes", clientes);
            return "lista-clientes";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar clientes", e);
        }
    }

    @GetMapping("/remover-cliente")
    public String removerCliente(@RequestParam String cpf) {
        try {
            service.removerCliente(cpf);
            return "redirect:/clientes";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover cliente", e);
        }
    }
}
