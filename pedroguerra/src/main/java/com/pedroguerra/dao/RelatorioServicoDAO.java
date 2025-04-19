package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.RelatorioServico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioServicoDAO {

    public void inserir(RelatorioServico relatorio) throws SQLException {
        String sql = "INSERT INTO RelatorioServico (fk_Servico_id, area, data_relatorio, observacoes) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, relatorio.getFkServicoId());
            stmt.setFloat(2, relatorio.getArea());
            stmt.setDate(3, relatorio.getDataRelatorio());
            stmt.setString(4, relatorio.getObservacoes());

            stmt.executeUpdate();
        }
    }

    public void atualizar(RelatorioServico relatorio) throws SQLException {
        String sql = "UPDATE RelatorioServico SET area = ?, data_relatorio = ?, observacoes = ? WHERE fk_Servico_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, relatorio.getArea());
            stmt.setDate(2, relatorio.getDataRelatorio());
            stmt.setString(3, relatorio.getObservacoes());
            stmt.setString(4, relatorio.getFkServicoId());

            stmt.executeUpdate();
        }
    }

    public void removerPorServicoId(String servicoId) throws SQLException {
        String sql = "DELETE FROM RelatorioServico WHERE fk_Servico_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicoId);
            stmt.executeUpdate();
        }
    }

     public RelatorioServico buscarPorServicoId(String servicoId) throws SQLException {
        String sql = "SELECT fk_Servico_id, area, data_relatorio, observacoes " +
                     "FROM RelatorioServico WHERE fk_Servico_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RelatorioServico rel = new RelatorioServico();
                    rel.setFkServicoId(rs.getString("fk_Servico_id"));
                    rel.setArea(rs.getFloat("area"));
                    rel.setDataRelatorio(rs.getDate("data_relatorio"));
                    rel.setObservacoes(rs.getString("observacoes"));
                    return rel;
                }
                return null;
            }
        }
    }
}
