package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Engenheiro;

public class EngenheiroDAO {

    private List<Engenheiro> engenheiros = new ArrayList<>();

    public boolean adicionar(Engenheiro e) {
        return engenheiros.add(e);
    }

    public List<Engenheiro> listarTodos() {
        return engenheiros;
    }

    public Engenheiro buscarPorMatricula(String matricula) {
        for (Engenheiro e : engenheiros) {
            if (e.getMatricula().equals(matricula)) return e;
        }
        return null;
    }
}
