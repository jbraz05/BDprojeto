package com.pedroguerra.service;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.RelatorioServicoDAO;
import com.pedroguerra.dao.ServicoDAO;
import com.pedroguerra.dto.ServicoDTO;
import com.pedroguerra.model.RelatorioServico;
import com.pedroguerra.model.Servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServicoService {

    private final ServicoDAO dao = new ServicoDAO();
    private final RelatorioServicoDAO relatorioDAO = new RelatorioServicoDAO();

    public void salvarServico(Servico servico) throws SQLException {
        dao.inserir(servico);
    }

    public void atualizarServico(Servico servico) throws SQLException {
        dao.atualizar(servico);
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

    public void removerServico(String id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Remove o RelatorioServico, se existir
                String sqlRelatorio = "DELETE FROM RelatorioServico WHERE fk_Servico_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlRelatorio)) {
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                }

                // Remove de VooPanoramico
                String sqlVoo = "DELETE FROM VooPanoramico WHERE fk_Servico_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlVoo)) {
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                }

                // Remove de MapeamentoTradicional
                String sqlMap = "DELETE FROM MapeamentoTradicional WHERE fk_Servico_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlMap)) {
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                }

                // Remove da tabela Servico
                String sqlServico = "DELETE FROM Servico WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlServico)) {
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void removerVooPanoramico(String idServico) throws SQLException {
        String sql = "DELETE FROM VooPanoramico WHERE fk_Servico_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idServico);
            stmt.executeUpdate();
        }
    }

    public void removerMapeamentoTradicional(String idServico) throws SQLException {
        String sql = "DELETE FROM MapeamentoTradicional WHERE fk_Servico_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idServico);
            stmt.executeUpdate();
        }
    }

    public void salvarRelatorio(RelatorioServico relatorio) throws SQLException {
        relatorioDAO.inserir(relatorio);
    }

    public void atualizarRelatorio(RelatorioServico relatorio) throws SQLException {
        relatorioDAO.atualizar(relatorio);
    }

    public void removerRelatorio(String servicoId) throws SQLException {
        relatorioDAO.removerPorServicoId(servicoId);
    }
}
