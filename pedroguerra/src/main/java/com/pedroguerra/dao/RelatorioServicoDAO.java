package com.pedroguerra.dao;

import java.util.*;
import com.pedroguerra.model.RelatorioServico;

public class RelatorioServicoDAO {

    private List<RelatorioServico> relatorios = new ArrayList<>();

    public boolean adicionar(RelatorioServico r) {
        return relatorios.add(r);
    }

    public List<RelatorioServico> listarTodos() {
        return relatorios;
    }

    public RelatorioServico buscarPorServico(String id) {
        for (RelatorioServico r : relatorios) {
            if (r.getfkServicoId().equals(id)) return r;
        }
        return null;
    }
}
