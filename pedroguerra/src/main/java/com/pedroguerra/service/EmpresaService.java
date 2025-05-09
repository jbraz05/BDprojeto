package com.pedroguerra.service;

import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dao.FuncionarioDAO;
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
                // 1. Buscar todos os funcionários da empresa
                List<String> matriculas = new ArrayList<>();
                try (PreparedStatement stmt = conn.prepareStatement(
                        "SELECT fk_Funcionario_matricula FROM Emprega WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        matriculas.add(rs.getString("fk_Funcionario_matricula"));
                    }
                }
    
                // 2. Remover todos os funcionários com lógica completa e conexão compartilhada
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                for (String matricula : matriculas) {
                    funcionarioDAO.removerPorMatriculaComConexao(matricula, conn);
                }
    
                // 3. Buscar IDs de serviços da empresa
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
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Possui WHERE fk_Servico_id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
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
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Servico WHERE id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                }
    
                // 4. Remover vínculos com localização
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Atua WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }
    
                // 5. Salvar o CEP da empresa antes de apagar
                String cep = null;
                try (PreparedStatement stmt = conn.prepareStatement("SELECT fk_endereco_cep FROM Empresa WHERE cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    ResultSet rsCep = stmt.executeQuery();
                    if (rsCep.next()) {
                        cep = rsCep.getString("fk_endereco_cep");
                    }
                }
    
                // 6. Remover a empresa
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Empresa WHERE cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }
    
                // 7. Remover o contato da empresa
                contatoDAO.remover("CTE_" + cnpj, conn);
    
                // 8. Remover o endereço se não estiver sendo usado
                if (cep != null) {
                    try (PreparedStatement checkStmt = conn.prepareStatement("""
                        SELECT (
                            (SELECT COUNT(*) FROM Empresa WHERE fk_endereco_cep = ?) +
                            (SELECT COUNT(*) FROM Funcionario WHERE fk_endereco_cep = ?) +
                            (SELECT COUNT(*) FROM Cliente WHERE fk_endereco_cep = ?)
                        ) AS total_uso
                    """)) {
                        checkStmt.setString(1, cep);
                        checkStmt.setString(2, cep);
                        checkStmt.setString(3, cep);
                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt("total_uso") == 0) {
                            try (PreparedStatement deleteEnderecoStmt = conn.prepareStatement(
                                    "DELETE FROM Endereco WHERE cep = ?")) {
                                deleteEnderecoStmt.setString(1, cep);
                                deleteEnderecoStmt.executeUpdate();
                            }
                        }
                    }
                }
    
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

    public List<EmpresaDTO> listarOrdenadoPorNome() throws SQLException {
        return empresaDAO.listarTodasComLocalizacaoOrdenado("nome");
    }
    
    public List<EmpresaDTO> listarOrdenadoPorCapital() throws SQLException {
        return empresaDAO.listarTodasComLocalizacaoOrdenado("capital_social");
    }
    
}
