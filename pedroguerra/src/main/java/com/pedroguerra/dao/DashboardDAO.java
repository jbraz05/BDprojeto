package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardDAO {

    public Map<String, Integer> getTopClientesPorQuantidade() throws SQLException {
        String sql = """
            SELECT c.nome, COUNT(*) AS total
            FROM Cliente c
            JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
            JOIN Servico s ON s.id = ct.nota_fiscal
            GROUP BY c.nome
            ORDER BY total DESC
            LIMIT 5
        """;

        Map<String, Integer> resultado = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getInt("total"));
            }
        }
        return resultado;
    }

    public Map<String, Double> getTopServicosPorValor() throws SQLException {
        String sql = """
            SELECT s.tipo, SUM(s.valor_medicao) AS total
            FROM Servico s
            GROUP BY s.tipo
            ORDER BY total DESC
            LIMIT 5
        """;

        Map<String, Double> resultado = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("tipo"), rs.getDouble("total"));
            }
        }
        return resultado;
    }

    public Map<String, Integer> getStatusServicos() throws SQLException {
        String sql = """
            SELECT feito, COUNT(*) AS total
              FROM Servico
             GROUP BY feito
        """;

        Map<String, Integer> status = new LinkedHashMap<>();
        status.put("Concluídos", 0);
        status.put("Pendentes",  0);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                boolean feito = rs.getBoolean("feito");
                int total = rs.getInt("total");
                if (feito) {
                    status.put("Concluídos", total);
                } else {
                    status.put("Pendentes", total);
                }
            }
        }
        return status;
    }

    public Map<String, BigDecimal> getReceitaMensal(int ano) throws SQLException {
        String sql = """
            SELECT MONTH(data_emissao_medicao) AS mes,
                   SUM(valor_medicao)       AS total
              FROM Servico
             WHERE YEAR(data_emissao_medicao) = ?
             GROUP BY mes
             ORDER BY mes
        """;

        // Meses em ordem de Janeiro a Dezembro
        String[] meses = {
            "Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
            "Jul", "Ago", "Set", "Out", "Nov", "Dez"
        };

        // Inicializa com zero para garantir todos os meses
        Map<String, BigDecimal> receita = new LinkedHashMap<>();
        for (String m : meses) {
            receita.put(m, BigDecimal.ZERO);
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ano);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int numMes = rs.getInt("mes");           // 1–12
                    BigDecimal total = rs.getBigDecimal("total");
                    String nomeMes = meses[numMes - 1];
                    receita.put(nomeMes, total);
                }
            }
        }
        return receita;
    }

    public Map<String, BigDecimal> getTopClientesPorReceita(int ano) throws SQLException {
        String sql = """
            SELECT c.nome      AS cliente,
                   SUM(s.valor_medicao) AS total
              FROM Contrata ct
              JOIN Cliente c
                ON ct.fk_cliente_cnpj_cpf = c.cnpj_cpf
              JOIN Servico s
                ON s.id = ct.nota_fiscal
             WHERE YEAR(s.data_emissao_medicao) = ?
             GROUP BY c.nome
             ORDER BY total DESC
             LIMIT 5
        """;

        Map<String, BigDecimal> top = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String cliente = rs.getString("cliente");
                    BigDecimal total = rs.getBigDecimal("total");
                    top.put(cliente, total);
                }
            }
        }
        return top;
    }

    public Map<String, Integer> getDistribuicaoServicosPorTipo() throws SQLException {
        String sql = """
            SELECT tipo, COUNT(*) AS total
              FROM Servico
             GROUP BY tipo
             ORDER BY total DESC
        """;

        Map<String, Integer> distrib = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo  = rs.getString("tipo");
                int total     = rs.getInt("total");
                distrib.put(tipo, total);
            }
        }
        return distrib;
    }

    public Map<String, BigDecimal> getMediaSalarioPorFuncao() throws SQLException {
        String sqlSocio = """
            SELECT AVG(f.salario) AS media
              FROM Funcionario f
              JOIN Socio s
                ON f.matricula = s.fk_Funcionario_matricula
        """;
        String sqlEng  = sqlSocio.replace("Socio s", "Engenheiro s");
        String sqlOp   = sqlSocio.replace("Socio s", "OperadorDrone s");

        Map<String, BigDecimal> medias = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Sócio
            try (PreparedStatement ps = conn.prepareStatement(sqlSocio);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) medias.put("Sócio", rs.getBigDecimal("media"));
            }
            // Engenheiro
            try (PreparedStatement ps = conn.prepareStatement(sqlEng);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) medias.put("Engenheiro", rs.getBigDecimal("media"));
            }
            // Operador de Drone
            try (PreparedStatement ps = conn.prepareStatement(sqlOp);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) medias.put("Operador de Drone", rs.getBigDecimal("media"));
            }
        }
        return medias;
    }


    public Map<String, Integer> getCepsMaisUsadosEmServicos() throws SQLException {
        String sql = """
            SELECT cep, COUNT(*) AS total FROM (
                SELECT e.cep FROM Servico s
                JOIN Possui p ON s.id = p.fk_servico_id
                JOIN Empresa emp ON emp.cnpj = p.fk_empresa_cnpj
                JOIN Endereco e ON e.cep = emp.fk_endereco_cep
    
                UNION ALL
    
                SELECT e.cep FROM Servico s
                JOIN Funcionario f ON f.matricula = s.fk_funcionario_matricula
                JOIN Endereco e ON e.cep = f.fk_endereco_cep
    
                UNION ALL
    
                SELECT e.cep FROM Servico s
                JOIN Contrata c ON c.nota_fiscal = s.id
                JOIN Cliente cli ON cli.cnpj_cpf = c.fk_cliente_cnpj_cpf
                JOIN Endereco e ON e.cep = cli.fk_endereco_cep
            ) AS ceps_total
            GROUP BY cep
            ORDER BY total DESC
            LIMIT 10
        """;
    
        Map<String, Integer> resultado = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("cep"), rs.getInt("total"));
            }
        }
        return resultado;
    }
    
    public List<Integer> getAnosComServico() throws SQLException {
        String sql = """
            SELECT DISTINCT YEAR(data_emissao_medicao) AS ano
            FROM Servico
            WHERE data_emissao_medicao IS NOT NULL
            ORDER BY ano
        """;
        List<Integer> anos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                anos.add(rs.getInt("ano"));
            }
        }
        return anos;
    }

    public Map<String, BigDecimal> getReceitaPorLocalizacao() throws SQLException {
        String sql = """
            SELECT CONCAT(l.nome_estado, ', ', l.nome_pais) AS localizacao,
                   SUM(s.valor_medicao) AS total
            FROM Servico s
            JOIN Possui p ON s.id = p.fk_servico_id
            JOIN LocalizacaoAtuacao l ON p.fk_localizacao_atuacao_codigo = l.codigo
            GROUP BY l.nome_estado, l.nome_pais
            ORDER BY total DESC
        """;
    
        Map<String, BigDecimal> resultado = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("localizacao"), rs.getBigDecimal("total"));
            }
        }
        return resultado;
    }
    
    
}
