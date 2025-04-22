package com.pedroguerra.controller;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Endereco;
import com.pedroguerra.model.LocalizacaoAtuacao;
import com.pedroguerra.model.Contato;
import com.pedroguerra.dto.EmpresaDTO;
import com.pedroguerra.service.EmpresaService;
import com.pedroguerra.service.EnderecoService;
import com.pedroguerra.service.LocalizacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@Controller
public class EmpresaController {

    private final EmpresaService empresaService = new EmpresaService();
    private final LocalizacaoService localizacaoService = new LocalizacaoService();
    private final EnderecoService enderecoService = new EnderecoService();
//l
    private void carregarLocalizacoes(Model model) {
        try {
            List<LocalizacaoAtuacao> localizacoes = localizacaoService.listar();
            model.addAttribute("localizacoes", localizacoes);
        } catch (SQLException e) {
            model.addAttribute("erro", "Erro ao carregar localizações.");
        }
    }

    @GetMapping("/empresa")
    public String abrirFormularioCadastroEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("contato", new Contato());
        carregarLocalizacoes(model);
        return "empresa";
    }

    @PostMapping("/empresa/cadastrar")
    public String cadastrarEmpresa(@ModelAttribute Empresa empresa,
                               @RequestParam String localizacaoCodigo,
                               @RequestParam String cep,
                               @RequestParam String rua,
                               @RequestParam String numero,
                               @RequestParam String bairro,
                               @RequestParam String cidade,
                               @RequestParam String telefone,
                               @RequestParam String email,
                               Model model) {
    try {
        Endereco endereco = enderecoService.buscar(cep);
        if (endereco == null) {
            endereco = new Endereco(cep, numero, cidade, bairro, rua);
            enderecoService.cadastrar(endereco);
        }

        empresa.setFkEnderecoCep(cep);
        Contato contato = new Contato("CTE_" + empresa.getCnpj(), telefone, email, null, empresa.getCnpj());

        empresaService.salvarEmpresa(empresa, contato, localizacaoCodigo);
        return "redirect:/empresa/listar";
    } catch (SQLException e) {
        model.addAttribute("erro", "Erro ao cadastrar empresa: " + e.getMessage());
        model.addAttribute("empresa", new Empresa());
        return "empresa";
    }
}

    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        try {
            List<EmpresaDTO> empresas = empresaService.listarTodasComLocalizacao();
            model.addAttribute("empresas", empresas);
            return "lista-empresas";
        } catch (SQLException e) {
            model.addAttribute("erro", "Erro ao listar empresas: " + e.getMessage());
            return "lista-empresas";
        }
    }

    @PostMapping("/empresa/remover")
    public String removerEmpresa(@RequestParam String cnpj, Model model) {
        try {
            empresaService.removerEmpresa(cnpj);
        } catch (SQLException e) {
            e.printStackTrace(); // ou log
        }
        return "redirect:/empresa/listar";
    }

    @GetMapping("/empresa/editar")
    public String abrirFormularioEdicaoEmpresa(@RequestParam String cnpj, Model model) {
        try {
            EmpresaDTO dto = empresaService.buscarEmpresa(cnpj);
            Empresa empresa = new Empresa();
            empresa.setCnpj(dto.getCnpj());
            empresa.setNome(dto.getNome());
            empresa.setFkEnderecoCep(dto.getCep());
    
            Endereco endereco = enderecoService.buscar(dto.getCep());
            List<Contato> contatos = empresaService.listarContatos(dto.getCnpj());
            Contato contato = contatos.isEmpty() ? new Contato() : contatos.get(0); // <-- garante que nunca será null
    
            model.addAttribute("empresa", empresa);
            model.addAttribute("endereco", endereco);
            model.addAttribute("contato", contato); // <-- ESSENCIAL PRA FUNCIONAR
            carregarLocalizacoes(model);
    
            return "empresa";
        } catch (SQLException e) {
            model.addAttribute("erro", "Erro ao buscar empresa: " + e.getMessage());
            return "empresa";
        }
    }
    

    @PostMapping("/empresa/atualizar")
    public String atualizarEmpresa(@ModelAttribute Empresa empresa,
                                   @RequestParam String localizacaoCodigo,
                                   @RequestParam String cep,
                                   @RequestParam String rua,
                                   @RequestParam String numero,
                                   @RequestParam String bairro,
                                   @RequestParam String cidade,
                                   @RequestParam String telefone,
                                   @RequestParam String email,
                                   Model model) {
        try {
            Endereco endereco = enderecoService.buscar(cep);
            if (endereco == null) {
                endereco = new Endereco(cep, numero, cidade, bairro, rua);
                enderecoService.cadastrar(endereco); // <- método correto aqui
            }
    
            empresa.setFkEnderecoCep(cep);
            Contato contato = new Contato("CTE_" + empresa.getCnpj(), telefone, email, null, empresa.getCnpj());
    
            empresaService.atualizarEmpresa(empresa, contato);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            model.addAttribute("erro", "Erro ao atualizar empresa: " + e.getMessage());
            model.addAttribute("empresa", empresa);
            return "empresa";
        }
    }
}
