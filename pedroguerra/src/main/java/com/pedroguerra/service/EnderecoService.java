package com.pedroguerra.service;

import com.pedroguerra.dao.EnderecoDAO;
import com.pedroguerra.model.Endereco;

import java.sql.SQLException;

public class EnderecoService {
    private final EnderecoDAO dao = new EnderecoDAO();

    public void cadastrar(Endereco endereco) throws SQLException {
        if (endereco.getCep() == null || endereco.getCep().isBlank()) {
            throw new SQLException("CEP não pode ser vazio");
        }
        dao.inserir(endereco);
    }

    public Endereco buscar(String cep) throws SQLException {
        return dao.buscarPorCep(cep);
    }
}