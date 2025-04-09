package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.Socio;

public class SocioDAO {

    private List<Socio> socios = new ArrayList<>();

    public boolean adicionar(Socio s) {
        return socios.add(s);
    }

    public Socio buscarPorMatricula(String matricula) {
        for (Socio s : socios) {
            if (s.getMatricula().equals(matricula)) return s;
        }
        return null;
    }

    public List<Socio> listarTodos() {
        return socios;
    }
}
