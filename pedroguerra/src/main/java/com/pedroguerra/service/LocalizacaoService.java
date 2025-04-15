package com.pedroguerra.service;

import com.pedroguerra.dao.LocalizacaoAtuacaoDAO;
import com.pedroguerra.model.LocalizacaoAtuacao;

import java.sql.SQLException;
import java.util.List;

public class LocalizacaoService {
    private final LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();

    public void cadastrar(LocalizacaoAtuacao l) throws SQLException {
        if (l.getCodigo() == null || l.getCodigo().isBlank()) {
            throw new SQLException("Código da localização inválido");
        }
        dao.inserir(l);
    }

    public List<LocalizacaoAtuacao> listar() throws SQLException {
        return dao.listarTodas();
    }

    public void remover(String codigo) throws SQLException {
        dao.removerPorCodigo(codigo);
    }

    public void atualizar(LocalizacaoAtuacao l) throws SQLException {
        dao.atualizar(l);
    }

    public LocalizacaoAtuacao buscarPorCodigo(String codigo) throws SQLException {
        return dao.buscarPorCodigo(codigo);
    }
}
