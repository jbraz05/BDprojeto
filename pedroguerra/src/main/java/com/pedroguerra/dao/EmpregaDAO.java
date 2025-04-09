package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Emprega;

public class EmpregaDAO {

    private List<Emprega> registros = new ArrayList<>();

    public boolean adicionar(Emprega e) {
        return registros.add(e);
    }

    public List<Emprega> listarPorEmpresa(String cnpj) {
        List<Emprega> result = new ArrayList<>();
        for (Emprega e : registros) {
            if (e.getEmpresaCnpj().equals(cnpj)) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Emprega> listarTodos() {
        return registros;
    }
}
