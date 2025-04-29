package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Contato;
import com.pedroguerra.dto.EmpresaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void inserir(Empresa empresa, Contato contato) throws SQLException {
        String sql = "INSERT INTO Empresa (cnpj, nome, capital_social, fk_endereco_cep) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, empresa.getNome());
            stmt.setFloat(3, empresa.getCapitalSocial());
            stmt.setString(4, empresa.getFkEnderecoCep());
            stmt.executeUpdate();

            if (contato != null) {
                contatoDAO.inserir(contato, conn);
            }
        }
    }

    public void atualizar(Empresa empresa, Contato novoContato) throws SQLException {
        String sql = "UPDATE Empresa SET nome = ?, capital_social = ?, fk_endereco_cep = ? WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setFloat(2, empresa.getCapitalSocial());
            stmt.setString(3, empresa.getFkEnderecoCep());
            stmt.setString(4, empresa.getCnpj());
            stmt.executeUpdate();

            if (novoContato != null) {
                contatoDAO.remover(novoContato.getCodigo(), conn);
                contatoDAO.inserir(novoContato, conn);
            }
        }
    }

    public void removerPorCnpj(String cnpj) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                List<String> idsServicos = new ArrayList<>();
                try (PreparedStatement stmt = conn.prepareStatement("SELECT fk_Servico_id FROM Possui WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        idsServicos.add(rs.getString("fk_Servico_id"));
                    }
                }

                for (String idServico : idsServicos) {
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
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contrata WHERE nota_fiscal = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Servico WHERE id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Emprega WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Atua WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Possui WHERE fk_Empresa_cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Empresa WHERE cnpj = ?")) {
                    stmt.setString(1, cnpj);
                    stmt.executeUpdate();
                }

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

    public EmpresaDTO buscarPorCnpj(String cnpj) throws SQLException {
        String sql = "SELECT e.*, en.rua, en.numero, en.bairro, en.cidade FROM Empresa e JOIN Endereco en ON e.fk_endereco_cep = en.cep WHERE e.cnpj = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                EmpresaDTO dto = new EmpresaDTO();
                dto.setCnpj(rs.getString("cnpj"));
                dto.setNome(rs.getString("nome"));
                dto.setCapitalSocial(rs.getFloat("capital_social"));
                dto.setCep(rs.getString("fk_endereco_cep"));
                dto.setRua(rs.getString("rua"));
                dto.setNumero(rs.getString("numero"));
                dto.setBairro(rs.getString("bairro"));
                dto.setCidade(rs.getString("cidade"));

                List<Contato> contatos = contatoDAO.listarPorEmpresa(cnpj);
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }
                return dto;
            }
        }
        return null;
    }

    public List<EmpresaDTO> listarTodasComLocalizacao() throws SQLException {
        List<EmpresaDTO> lista = new ArrayList<>();
        String sql = """
            SELECT e.cnpj, e.nome, e.capital_social,
                   en.cep, en.rua, en.numero, en.bairro, en.cidade,
                   l.nome_estado, l.nome_pais, l.regiao
            FROM Empresa e
            JOIN Endereco en ON e.fk_endereco_cep = en.cep
            LEFT JOIN Atua a ON e.cnpj = a.fk_Empresa_cnpj
            LEFT JOIN LocalizacaoAtuacao l ON l.codigo = a.fk_Localizacao_Atuacao_codigo
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EmpresaDTO dto = new EmpresaDTO();
                dto.setCnpj(rs.getString("cnpj"));
                dto.setNome(rs.getString("nome"));
                dto.setCapitalSocial(rs.getFloat("capital_social"));
                dto.setCep(rs.getString("cep"));
                dto.setRua(rs.getString("rua"));
                dto.setNumero(rs.getString("numero"));
                dto.setBairro(rs.getString("bairro"));
                dto.setCidade(rs.getString("cidade"));
                dto.setNomeEstado(rs.getString("nome_estado"));
                dto.setNomePais(rs.getString("nome_pais"));
                dto.setRegiao(rs.getString("regiao"));

                List<Contato> contatos = contatoDAO.listarPorEmpresa(dto.getCnpj());
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }

                lista.add(dto);
            }
        }

        return lista;
    }

    public List<Empresa> listarTodas() throws SQLException {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empresa";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empresa e = new Empresa();
                e.setCnpj(rs.getString("cnpj"));
                e.setNome(rs.getString("nome"));
                e.setCapitalSocial(rs.getFloat("capital_social"));
                e.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                lista.add(e);
            }
        }
        return lista;
    }
}
