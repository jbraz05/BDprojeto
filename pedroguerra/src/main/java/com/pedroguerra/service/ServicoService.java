package com.pedroguerra.service;

import com.pedroguerra.dao.ServicoDAO;
import com.pedroguerra.dto.ServicoDTO;
import com.pedroguerra.model.Servico;

import java.sql.SQLException;
import java.util.List;

public class ServicoService {

    private final ServicoDAO dao = new ServicoDAO();

    public void salvarServico(Servico servico) throws SQLException {
        dao.inserir(servico);
    }

    public void atualizarServico(Servico servico) throws SQLException {
        dao.atualizar(servico);
    }

    public void removerServico(String id) throws SQLException {
        dao.removerPorId(id);
    }

    public ServicoDTO buscarServicoDTO(String id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public boolean servicoExiste(String id) throws SQLException {
        return dao.buscarPorId(id) != null;
    }

    public List<ServicoDTO> listarTodosDTO() throws SQLException {
        return dao.listarTodosDTO();
    }
}