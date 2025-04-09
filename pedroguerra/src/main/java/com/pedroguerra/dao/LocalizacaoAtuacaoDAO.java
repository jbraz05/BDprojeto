package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.LocalizacaoAtuacao;

public class LocalizacaoAtuacaoDAO {

    private List<LocalizacaoAtuacao> locais = new ArrayList<>();

    public boolean adicionar(LocalizacaoAtuacao l) {
        return locais.add(l);
    }

    public LocalizacaoAtuacao buscarPorCodigo(String codigo) {
        for (LocalizacaoAtuacao l : locais) {
            if (l.getCodigo().equals(codigo)) return l;
        }
        return null;
    }

    public List<LocalizacaoAtuacao> listarTodos() {
        return locais;
    }
}
