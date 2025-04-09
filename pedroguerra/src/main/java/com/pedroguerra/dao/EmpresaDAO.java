package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Empresa;

public class EmpresaDAO {

    private List<Empresa> empresas = new ArrayList<>();

    public boolean adicionar(Empresa e) {
        return empresas.add(e);
    }

    public Empresa buscarPorCnpj(String cnpj) {
        for (Empresa e : empresas) {
            if (e.getCnpj().equals(cnpj)) return e;
        }
        return null;
    }

    public List<Empresa> listarTodos() {
        return empresas;
    }
}
