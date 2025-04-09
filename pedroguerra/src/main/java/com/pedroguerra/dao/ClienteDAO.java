package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Cliente;

public class ClienteDAO {

    private List<Cliente> clientes = new ArrayList<>();

    public boolean adicionar(Cliente cliente) {
        return clientes.add(cliente);
    }

    public Cliente buscarPorDocumento(String doc) {
        for (Cliente c : clientes) {
            if (c.getCnpjCpf().equals(doc)) return c;
        }
        return null;
    }
    public List<Cliente> listarTodos() {
        return clientes;
    }
}
