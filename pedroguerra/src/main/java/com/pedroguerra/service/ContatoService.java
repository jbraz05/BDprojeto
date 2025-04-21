package com.pedroguerra.service;

import com.pedroguerra.dao.ContatoDAO;
import com.pedroguerra.model.Contato;

import java.sql.SQLException;
import java.util.List;

public class ContatoService {

    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void salvarContato(Contato contato) throws SQLException {
        contatoDAO.inserir(contato);
    }

    public void removerContato(String codigo) throws SQLException {
        contatoDAO.remover(codigo);
    }

    public List<Contato> buscarContatosFuncionario(String matricula) throws SQLException {
        return contatoDAO.listarPorFuncionario(matricula);
    }

    public List<Contato> buscarContatosEmpresa(String cnpj) throws SQLException {
        return contatoDAO.listarPorEmpresa(cnpj);
    }
}
