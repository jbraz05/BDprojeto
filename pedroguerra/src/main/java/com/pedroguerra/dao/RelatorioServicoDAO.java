// RelatorioServicoDAO.java
package com.pedroguerra.dao;

import com.pedroguerra.model.RelatorioServico;
import com.pedroguerra.config.ConnectionFactory;

import java.sql.*;

public class RelatorioServicoDAO {

    public void inserir(RelatorioServico r) throws SQLException {
        String sql = "INSERT INTO RelatorioServico (fk_Servico_id, area, data, observacoes) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getfkServicoId());
            stmt.setFloat(2, r.getArea());
            stmt.setDate(3, new java.sql.Date(r.getDataRelatorio().getTime()));
            stmt.setString(4, r.getObservacoes());
            stmt.executeUpdate();
        }
    }
}
