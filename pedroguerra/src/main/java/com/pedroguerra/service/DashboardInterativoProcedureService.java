package com.pedroguerra.service;

import com.pedroguerra.dao.DashboardInterativoProcedureDAO;
import org.springframework.stereotype.Service;
    
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class DashboardInterativoProcedureService {

    private final DataSource dataSource;

    public DashboardInterativoProcedureService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, Double> obterDados(String entidade, String metrica) {
        try (Connection conn = dataSource.getConnection()) {
            DashboardInterativoProcedureDAO dao = new DashboardInterativoProcedureDAO(conn);
            return dao.obterDados(entidade, metrica);
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of();
        }
    }
}
