package com.pedroguerra.controller;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Endereco;
import com.pedroguerra.model.LocalizacaoAtuacao;
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
                                   Model model) {
        try {
            empresaService.processarCadastro(empresa, cep, rua, numero, bairro, cidade, localizacaoCodigo);
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
            List<EmpresaDTO> empresas = empresaService.listarEmpresasComEnderecoEAtuacao();
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
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            model.addAttribute("erro", "Erro ao remover empresa: " + e.getMessage());
            return "empresa";
        }
    }

    @GetMapping("/empresa/editar")
    public String abrirFormularioEdicaoEmpresa(@RequestParam String cnpj, Model model) {
        try {
            Empresa empresa = empresaService.buscarEmpresaPorCnpj(cnpj);
            model.addAttribute("empresa", empresa);

            Endereco endereco = enderecoService.buscar(empresa.getFkEnderecoCep());
            model.addAttribute("endereco", endereco);

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
                                   Model model) {
        try {
            empresaService.atualizarEmpresaComEndereco(empresa, cep, rua, numero, bairro, cidade, localizacaoCodigo);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            model.addAttribute("erro", "Erro ao atualizar empresa: " + e.getMessage());
            model.addAttribute("empresa", empresa);
            return "empresa";
        }
    }
}
