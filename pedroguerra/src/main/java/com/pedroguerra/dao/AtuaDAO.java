package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Atua;

public class AtuaDAO {

    private List<Atua> atuaList = new ArrayList<>();

    public boolean adicionar(Atua atua) {
        return atuaList.add(atua);
    }

    public List<Atua> listarTodos() {
        return atuaList;
    }

    public Atua buscarPorEmpresa(String cnpj) {
        for (Atua a : atuaList) {
            if (a.getEmpresaCnpj().equals(cnpj)) {
                return a;
            }
        }
        return null;
    }
}
