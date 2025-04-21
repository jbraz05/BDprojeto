package com.pedroguerra.service;

import com.pedroguerra.dao.ContatoDAO;
import com.pedroguerra.model.Contato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContatoService {

    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void salvarContato(Contato contato, Connection conn) throws SQLException {
        contatoDAO.inserir(contato,conn);
    }

    public void removerContato(String codigo,Connection conn) throws SQLException {
        contatoDAO.remover(codigo,conn);
    }

    public List<Contato> buscarContatosFuncionario(String matricula) throws SQLException {
        return contatoDAO.listarPorFuncionario(matricula);
    }

    public List<Contato> buscarContatosEmpresa(String cnpj) throws SQLException {
        return contatoDAO.listarPorEmpresa(cnpj);
    }
}
