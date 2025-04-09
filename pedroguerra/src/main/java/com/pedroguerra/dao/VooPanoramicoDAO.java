package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.VooPanoramico;

public class VooPanoramicoDAO {

    private List<VooPanoramico> voos = new ArrayList<>();

    public boolean adicionar(VooPanoramico v) {
        return voos.add(v);
    }

    public VooPanoramico buscarPorId(String id) {
        for (VooPanoramico v : voos) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    public List<VooPanoramico> listarTodos() {
        return voos;
    }

    public List<VooPanoramico> buscarPorFuncionario(String matricula) {
        List<VooPanoramico> resultado = new ArrayList<>();
        for (VooPanoramico v : voos) {
            if (v.getfkMatriculaFuncionario().equals(matricula)) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
