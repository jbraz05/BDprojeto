package com.pedroguerra.service;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.ServicoDAO;
import com.pedroguerra.dto.ServicoDTO;
import com.pedroguerra.model.Servico;
import java.sql.Connection;
import java.sql.PreparedStatement;


import java.sql.PreparedStatement;
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

    public void salvarVooPanoramico(String idServico, String matriculaOperador) throws SQLException {
    String sql = "INSERT INTO VooPanoramico (fk_Servico_id, fk_OperadorDrone_matricula) VALUES (?, ?)";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico);
        stmt.setString(2, matriculaOperador);
        stmt.executeUpdate();
    }
}

public void salvarMapeamentoTradicional(String idServico) throws SQLException {
    String sql = "INSERT INTO MapeamentoTradicional (fk_Servico_id) VALUES (?)";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico);
        stmt.executeUpdate();
    }
}

}