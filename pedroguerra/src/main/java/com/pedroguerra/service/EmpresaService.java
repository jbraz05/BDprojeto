package com.pedroguerra.service;

import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dto.EmpresaDTO;
import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.ContatoDAO;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Contato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaService {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void salvarEmpresa(Empresa empresa, Contato contato, String localizacaoCodigo) throws SQLException {
        empresaDAO.inserir(empresa, contato);

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Atua (fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo) VALUES (?, ?)")) {
            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, localizacaoCodigo);
            stmt.executeUpdate();
        }
}

    public void atualizarEmpresa(Empresa empresa, Contato novoContato) throws SQLException {
        empresaDAO.atualizar(empresa, novoContato);
    }

    public void removerEmpresa(String cnpj) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Buscar IDs de serviços vinculados à empresa via tabela Possui
                List<String> idsServicos = new ArrayList<>();
                String sqlServicos = "SELECT fk_Servico_id FROM Possui WHERE fk_Empresa_cnpj = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlServicos)) {
                    ps.setString(1, cnpj);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        idsServicos.add(rs.getString("fk_Servico_id"));
                    }
                }
    
                for (String idServico : idsServicos) {
                    // 1. Remover o vínculo da tabela Possui primeiro
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Possui WHERE fk_Servico_id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
    
                    // 2. Remover vínculos nas tabelas filhas
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contrata WHERE nota_fiscal = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
    
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM VooPanoramico WHERE fk_Servico_id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
    
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM MapeamentoTradicional WHERE fk_Servico_id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
    
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM RelatorioServico WHERE fk_Servico_id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
    
                    // 3. Por fim, remover o serviço
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Servico WHERE id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                }
    
                // Remover vínculos com funcionários
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Emprega WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }
    
                // Remover vínculos com localização
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Atua WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }
    
                // Remover a própria empresa
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Empresa WHERE cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }
    
                // Remover o contato da empresa
                contatoDAO.remover("CTE_" + cnpj, conn);
    
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
 
    
    public EmpresaDTO buscarEmpresa(String cnpj) throws SQLException {
        return empresaDAO.buscarPorCnpj(cnpj);
    }

    public List<Empresa> listarTodas() throws SQLException {
        return empresaDAO.listarTodas();
    }

    public List<EmpresaDTO> listarTodasComLocalizacao() throws SQLException {
        return empresaDAO.listarTodasComLocalizacao();
    }

    public List<Contato> listarContatos(String cnpj) throws SQLException {
        return contatoDAO.listarPorEmpresa(cnpj);
    }
}
