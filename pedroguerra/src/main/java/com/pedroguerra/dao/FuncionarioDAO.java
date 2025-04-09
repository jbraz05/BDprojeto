package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Funcionario;

public class FuncionarioDAO {

    private List<Funcionario> funcionarios = new ArrayList<>();

    public boolean adicionar(Funcionario f) {
        return funcionarios.add(f);
    }

    public Funcionario buscarPorMatricula(String matricula) {
        for (Funcionario f : funcionarios) {
            if (f.getMatricula().equals(matricula)) return f;
        }
        return null;
    }

    public List<Funcionario> listarTodos() {
        return funcionarios;
    }
}
