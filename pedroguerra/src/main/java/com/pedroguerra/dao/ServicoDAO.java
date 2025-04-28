package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.RelatorioServico;
import com.pedroguerra.model.Servico;
import com.pedroguerra.dto.ServicoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public boolean inserir(Servico servico) throws SQLException {
        String sql = "INSERT INTO Servico (id, data, tipo, fk_Funcionario_matricula) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getId());
            stmt.setDate(2, servico.getData());
            stmt.setString(3, servico.getTipo());
            stmt.setString(4, servico.getFkFuncionarioMatricula());
            stmt.executeUpdate();
            return true;
        }
    }

    public void atualizar(Servico servico) throws SQLException {
        String sql = "UPDATE Servico SET data = ?, tipo = ?, fk_Funcionario_matricula = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, servico.getData());
            stmt.setString(2, servico.getTipo());
            stmt.setString(3, servico.getFkFuncionarioMatricula());
            stmt.setString(4, servico.getId());
            stmt.executeUpdate();
        }
    }

    public boolean removerPorId(String id) throws SQLException {
        String sql = "DELETE FROM Servico WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public ServicoDTO buscarPorId(String id) throws SQLException {
        String sql = "SELECT s.*, f.nome AS nome_funcionario " +
                    "FROM Servico s " +
                    "JOIN Funcionario f ON s.fk_Funcionario_matricula = f.matricula " +
                    "WHERE s.id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ServicoDTO dto = new ServicoDTO();
                dto.setId(rs.getString("id"));
                dto.setData(rs.getDate("data"));
                dto.setTipo(rs.getString("tipo"));
                dto.setFkFuncionarioMatricula(rs.getString("fk_Funcionario_matricula"));
                dto.setNomeFuncionario(rs.getString("nome_funcionario"));

                RelatorioServicoDAO relDAO = new RelatorioServicoDAO();
                RelatorioServico relatorio = relDAO.buscarPorServicoId(id);
                if (relatorio != null) {
                    dto.setArea(relatorio.getArea());
                    dto.setDataRelatorio(relatorio.getDataRelatorio());
                    dto.setObservacoes(relatorio.getObservacoes());
                }

                return dto;
            } else {
                return null;
            }
        }
    }

    public List<ServicoDTO> listarTodosDTO() throws SQLException {
        List<ServicoDTO> lista = new ArrayList<>();
    
        String sql = "SELECT s.*, f.nome AS nome_funcionario, od.nome AS nome_operador " +
                     "FROM Servico s " +
                     "LEFT JOIN Funcionario f ON s.fk_Funcionario_matricula = f.matricula " +
                     "LEFT JOIN VooPanoramico vp ON s.id = vp.fk_Servico_id " +
                     "LEFT JOIN Funcionario od ON vp.fk_OperadorDrone_matricula = od.matricula";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                ServicoDTO dto = new ServicoDTO();
                dto.setId(rs.getString("id"));
                dto.setData(rs.getDate("data"));
                dto.setTipo(rs.getString("tipo"));
                dto.setFkFuncionarioMatricula(rs.getString("fk_Funcionario_matricula"));
                dto.setNomeFuncionario(rs.getString("nome_funcionario"));
                dto.setNomeOperadorDrone(rs.getString("nome_operador"));
    
                lista.add(dto);
            }
        }
    
        return lista;
    }
    

    public void atualizarClienteDoServico(String servicoId, String novoClienteCnpjCpf) throws SQLException {
        String verificarSQL = "SELECT 1 FROM Contrata WHERE fk_Servico_id = ?";
        String atualizarSQL = "UPDATE Contrata SET fk_Cliente_cnpj_cpf = ? WHERE fk_Servico_id = ?";
        String inserirSQL = "INSERT INTO Contrata (nota_fiscal, fk_Cliente_cnpj_cpf, fk_Servico_id) VALUES (?, ?, ?)";
    
        try (Connection conn = ConnectionFactory.getConnection()) {
            boolean existe;
            try (PreparedStatement verificarStmt = conn.prepareStatement(verificarSQL)) {
                verificarStmt.setString(1, servicoId);
                ResultSet rs = verificarStmt.executeQuery();
                existe = rs.next();
            }
    
            if (existe) {
                try (PreparedStatement atualizarStmt = conn.prepareStatement(atualizarSQL)) {
                    atualizarStmt.setString(1, novoClienteCnpjCpf);
                    atualizarStmt.setString(2, servicoId);
                    atualizarStmt.executeUpdate();
                }
            } else {
                String notaFiscalGerada = "NF_" + servicoId; 
                try (PreparedStatement inserirStmt = conn.prepareStatement(inserirSQL)) {
                    inserirStmt.setString(1, notaFiscalGerada);
                    inserirStmt.setString(2, novoClienteCnpjCpf);
                    inserirStmt.setString(3, servicoId);
                    inserirStmt.executeUpdate();
                }
            }
        }
    }
}
