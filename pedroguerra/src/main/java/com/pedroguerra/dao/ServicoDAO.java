package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Servico;

public class ServicoDAO {

    private List<Servico> servicos = new ArrayList<>();

    public boolean adicionar(Servico s) {
        return servicos.add(s);
    }

    public Servico buscarPorId(String id) {
        for (Servico s : servicos) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    public List<Servico> listarTodos() {
        return servicos;
    }

    public List<Servico> buscarPorFuncionario(String matricula) {
        List<Servico> resultado = new ArrayList<>();
        for (Servico s : servicos) {
            if (s.getfkMatriculaFuncionario().equals(matricula)) {
                resultado.add(s);
            }
        }
        return resultado;
    }
}
