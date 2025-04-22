package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;





@Controller
public class LocalizacaoController {

    @GetMapping("/localizacao")
    public String mostrarFormulario(Model model) {
        model.addAttribute("localizacao", new LocalizacaoAtuacao());
        return "localizacao";
    }

    @PostMapping("/localizacao/cadastrar")
    public String cadastrar(@ModelAttribute LocalizacaoAtuacao l, Model model) {
        try {
            LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
            dao.inserir(l);
            return "redirect:/localizacao/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            model.addAttribute("localizacao", l); // ← ESSENCIAL

            return "localizacao";
        }
    }

    @GetMapping("/localizacao/listar")
    public String listar(Model model) {
        try {
            LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
            List<LocalizacaoAtuacao> lista = dao.listarTodas();
            model.addAttribute("localizacoes", lista);
            return "lista-localizacao";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar: " + e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/localizacao/remover")
public String remover(@RequestParam String codigo, Model model) {
    try {
        LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
        dao.removerPorCodigo(codigo);
        return "redirect:/localizacao/listar";
    } catch (SQLException e) {
        e.printStackTrace();
        String mensagemErro;

        if (e.getMessage().contains("foreign key") || e.getMessage().toLowerCase().contains("constraint")) {
            mensagemErro = "Não é possível remover esta localização, pois ela está vinculada a uma empresa.";
        } else {
            mensagemErro = "Erro ao remover: " + e.getMessage();
        }

        try {
            LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
            List<LocalizacaoAtuacao> lista = dao.listarTodas();
            model.addAttribute("localizacoes", lista);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        model.addAttribute("erro", mensagemErro);
        return "lista-localizacao";
    }
}


    @GetMapping("/localizacao/editar")
    public String editar(@RequestParam String codigo, Model model) {
        try {
            LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
            LocalizacaoAtuacao localizacao = dao.buscarPorCodigo(codigo);
            model.addAttribute("localizacao", localizacao);
            return "localizacao"; // mesma página do form, reutilizada
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao buscar: " + e.getMessage());
            return "erro";
        }
    }

    @PostMapping("/localizacao/atualizar")
    public String atualizar(@ModelAttribute LocalizacaoAtuacao l, Model model) {
        try {
            LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
            dao.atualizar(l);
            return "redirect:/localizacao/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao atualizar: " + e.getMessage());
            return "erro";
        }
    }
}
