package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.OperadorDrone;

public class OperadorDroneDAO {

    private List<OperadorDrone> operadores = new ArrayList<>();

    public boolean adicionar(OperadorDrone o) {
        return operadores.add(o);
    }

    public List<OperadorDrone> listarTodos() {
        return operadores;
    }

    public OperadorDrone buscarPorMatricula(String matricula) {
        for (OperadorDrone o : operadores) {
            if (o.getMatricula().equals(matricula)) return o;
        }
        return null;
    }
}
