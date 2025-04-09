package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Contrata;

public class ContrataDAO {

    private List<Contrata> contratos = new ArrayList<>();

    public boolean adicionar(Contrata contrato) {
        return contratos.add(contrato);
    }

    public List<Contrata> listarTodos() {
        return contratos;
    }

    public List<Contrata> buscarPorCliente(String cnpjCpf) {
        List<Contrata> resultado = new ArrayList<>();
        for (Contrata c : contratos) {
            if (c.getcnpjCpf().equals(cnpjCpf)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}
