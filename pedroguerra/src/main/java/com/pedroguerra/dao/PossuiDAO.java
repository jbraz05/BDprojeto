package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Possui;

public class PossuiDAO {

    private List<Possui> registros = new ArrayList<>();

    public boolean adicionar(Possui p) {
        return registros.add(p);
    }

    public List<Possui> buscarPorEmpresa(String cnpj) {
        List<Possui> result = new ArrayList<>();
        for (Possui p : registros) {
            if (p.getEmpresaCnpj().equals(cnpj)) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Possui> listarTodos() {
        return registros;
    }
}
