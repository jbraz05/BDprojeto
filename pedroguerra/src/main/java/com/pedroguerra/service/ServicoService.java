package com.pedroguerra.service;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.RelatorioServicoDAO;
import com.pedroguerra.dao.ServicoDAO;
import com.pedroguerra.dto.ServicoDTO;
import com.pedroguerra.model.RelatorioServico;
import com.pedroguerra.model.Servico;

import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServicoService {

    private final ServicoDAO dao = new ServicoDAO();
    private final RelatorioServicoDAO relatorioDAO = new RelatorioServicoDAO();
    
    
    public void atualizarServico(Servico servico) throws SQLException {
        dao.atualizar(servico);
    }
    public void salvarServico(Servico servico) throws SQLException {
        dao.inserir(servico);
    }
    public void atualizarRelatorio(String idServico, BigDecimal area, Date data, String obs) throws SQLException {
        RelatorioServico rel = new RelatorioServico(idServico, area, data, obs);
        relatorioDAO.atualizar(rel);
    }
    
    public void salvarRelatorio(String idServico, BigDecimal area, Date data, String obs) throws SQLException {
        RelatorioServico rel = new RelatorioServico(idServico, area, data, obs);
        relatorioDAO.inserir(rel);
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

    public void removerRelatorio(String servicoId) throws SQLException {
        relatorioDAO.removerPorServicoId(servicoId);
    }

    public RelatorioServico buscarRelatorio(String servicoId) throws SQLException {
        return relatorioDAO.buscarPorServicoId(servicoId);
    }

    public void vincularEmpresaLocalizacaoAoServico(String idServico, String cnpj, String codigoLocalizacao) throws SQLException {
        String sql = "INSERT INTO Possui (fk_Servico_id, fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idServico);
            stmt.setString(2, cnpj);
            stmt.setString(3, codigoLocalizacao);
            stmt.executeUpdate();
        }
    }

    public void removerVinculoPossui(String idServico) throws SQLException {
    String sql = "DELETE FROM Possui WHERE fk_Servico_id = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico);
        stmt.executeUpdate();
    }
}

public String[] buscarEmpresaEAtuacaoDoServico(String idServico) throws SQLException {
    String sql = "SELECT fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo FROM Possui WHERE fk_Servico_id = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new String[]{ rs.getString("fk_Empresa_cnpj"), rs.getString("fk_Localizacao_Atuacao_codigo") };
        }
    }
    return null;
}

public void vincularClienteAoServico(String idServico, String cnpjCpf) throws SQLException {
    String sql = "INSERT INTO Contrata (nota_fiscal, fk_Cliente_cnpj_cpf) VALUES (?, ?)";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico); // idServico = nota_fiscal
        stmt.setString(2, cnpjCpf);
        stmt.executeUpdate();
    }
}

public void atualizarClienteDoServico(String idServico, String novoCnpjCpf) throws SQLException {
    String sql = "UPDATE Contrata SET fk_Cliente_cnpj_cpf = ? WHERE nota_fiscal = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, novoCnpjCpf);
        stmt.setString(2, idServico);
        stmt.executeUpdate();
    }
}

public void removerClienteDoServico(String idServico) throws SQLException {
    String sql = "DELETE FROM Contrata WHERE nota_fiscal = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico);
        stmt.executeUpdate();
    }
}

public String buscarClienteDoServico(String idServico) throws SQLException {
    String sql = "SELECT fk_Cliente_cnpj_cpf FROM Contrata WHERE nota_fiscal = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idServico);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("fk_Cliente_cnpj_cpf");
        }
    }
    return null;
}

    
}
