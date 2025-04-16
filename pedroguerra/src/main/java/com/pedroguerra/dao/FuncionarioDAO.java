package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dto.FuncionarioDTO;
import com.pedroguerra.model.Endereco;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.model.Emprega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public boolean inserir(FuncionarioDTO dto) throws SQLException {
        String sqlFuncionario = "INSERT INTO Funcionario (matricula, nome, contato, fk_endereco_cep, fk_supervisor_matricula) VALUES (?, ?, ?, ?, ?)";
        String sqlSocio = "INSERT INTO Socio (fk_Funcionario_matricula) VALUES (?)";
        String sqlEngenheiro = "INSERT INTO Engenheiro (fk_Funcionario_matricula) VALUES (?)";
        String sqlOperador = "INSERT INTO OperadorDrone (fk_Funcionario_matricula) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                if (!enderecoDAO.cepExiste(dto.getFkEnderecoCep())) {
                    Endereco endereco = new Endereco(
                        dto.getFkEnderecoCep(),
                        dto.getNumeroEndereco(),
                        dto.getCidadeEndereco(),
                        dto.getBairroEndereco(),
                        dto.getRuaEndereco()
                    );
                    enderecoDAO.inserir(endereco);
                }

                try (PreparedStatement stmt1 = conn.prepareStatement(sqlFuncionario)) {
                    stmt1.setString(1, dto.getMatricula());
                    stmt1.setString(2, dto.getNome());
                    stmt1.setString(3, dto.getContato());
                    stmt1.setString(4, dto.getFkEnderecoCep());

                    if (dto.getFkSupervisorMatricula() == null || dto.getFkSupervisorMatricula().isEmpty()) {
                        stmt1.setNull(5, Types.VARCHAR);
                    } else {
                        stmt1.setString(5, dto.getFkSupervisorMatricula());
                    }
                    stmt1.executeUpdate();
                }

                EmpregaDAO empregaDAO = new EmpregaDAO();
                Emprega emprega = new Emprega(dto.getCnpjEmpresa(), dto.getMatricula());
                empregaDAO.inserir(emprega, conn);

                if (dto.isSocio()) try (PreparedStatement s = conn.prepareStatement(sqlSocio)) { s.setString(1, dto.getMatricula()); s.executeUpdate(); }
                if (dto.isEngenheiro()) try (PreparedStatement s = conn.prepareStatement(sqlEngenheiro)) { s.setString(1, dto.getMatricula()); s.executeUpdate(); }
                if (dto.isOperadorDrone()) try (PreparedStatement s = conn.prepareStatement(sqlOperador)) { s.setString(1, dto.getMatricula()); s.executeUpdate(); }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void removerPorMatricula(String matricula) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Engenheiro WHERE fk_funcionario_matricula = ?")) {
                    stmt1.setString(1, matricula);
                    stmt1.executeUpdate();
                }
                try (PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Socio WHERE fk_funcionario_matricula = ?")) {
                    stmt2.setString(1, matricula);
                    stmt2.executeUpdate();
                }
                try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM OperadorDrone WHERE fk_funcionario_matricula = ?")) {
                    stmt3.setString(1, matricula);
                    stmt3.executeUpdate();
                }
                try (PreparedStatement stmt4 = conn.prepareStatement("DELETE FROM Emprega WHERE fk_funcionario_matricula = ?")) {
                    stmt4.setString(1, matricula);
                    stmt4.executeUpdate();
                }
                try (PreparedStatement stmt5 = conn.prepareStatement("UPDATE Funcionario SET fk_supervisor_matricula = NULL WHERE fk_supervisor_matricula = ?")) {
                    stmt5.setString(1, matricula);
                    stmt5.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Funcionario WHERE matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
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

    public List<Funcionario> listarPorEmpresa(String cnpj) throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.* FROM Funcionario f JOIN Emprega e ON f.matricula = e.fk_Funcionario_matricula WHERE e.fk_Empresa_cnpj = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario(
                    rs.getString("matricula"),
                    rs.getString("nome"),
                    rs.getString("contato"),
                    rs.getString("fk_endereco_cep"),
                    rs.getString("fk_supervisor_matricula")
                );
                lista.add(f);
            }
        }
        return lista;
    }

    public List<Funcionario> listarTodos() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario f = new Funcionario(
                    rs.getString("matricula"),
                    rs.getString("nome"),
                    rs.getString("contato"),
                    rs.getString("fk_endereco_cep"),
                    rs.getString("fk_supervisor_matricula")
                );
                lista.add(f);
            }
        }
        return lista;
    }

    public FuncionarioDTO buscarFuncionarioDTO(String matricula) throws SQLException {
        String sql = "SELECT f.*, e.rua, e.numero, e.cidade, e.bairro " +
                     "FROM Funcionario f JOIN Endereco e ON f.fk_endereco_cep = e.cep " +
                     "WHERE f.matricula = ?";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                FuncionarioDTO dto = new FuncionarioDTO();
                dto.setMatricula(rs.getString("matricula"));
                dto.setNome(rs.getString("nome"));
                dto.setContato(rs.getString("contato"));
                dto.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                dto.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
    
                dto.setRuaEndereco(rs.getString("rua"));
                dto.setNumeroEndereco(rs.getString("numero"));
                dto.setCidadeEndereco(rs.getString("cidade"));
                dto.setBairroEndereco(rs.getString("bairro"));
    
                // Você pode recuperar também o CNPJ da empresa se quiser
    
                return dto;
            } else {
                return null;
            }
        }
    }
    
    public void atualizar(FuncionarioDTO dto) throws SQLException {
        String sql = "UPDATE Funcionario SET nome = ?, contato = ?, fk_endereco_cep = ?, fk_supervisor_matricula = ? WHERE matricula = ?";
    
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                if (!enderecoDAO.cepExiste(dto.getFkEnderecoCep())) {
                    Endereco novo = new Endereco(
                        dto.getFkEnderecoCep(),
                        dto.getNumeroEndereco(),
                        dto.getCidadeEndereco(),
                        dto.getBairroEndereco(),
                        dto.getRuaEndereco()
                    );
                    enderecoDAO.inserir(novo);
                }
    
                // Atualiza os dados principais
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, dto.getNome());
                    stmt.setString(2, dto.getContato());
                    stmt.setString(3, dto.getFkEnderecoCep());
    
                    if (dto.getFkSupervisorMatricula() == null || dto.getFkSupervisorMatricula().isEmpty()) {
                        stmt.setNull(4, Types.VARCHAR);
                    } else {
                        stmt.setString(4, dto.getFkSupervisorMatricula());
                    }
    
                    stmt.setString(5, dto.getMatricula());
                    stmt.executeUpdate();
                }
    
                // Atualiza Emprega
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Emprega WHERE fk_Funcionario_matricula = ?")) {
                    stmt.setString(1, dto.getMatricula());
                    stmt.executeUpdate();
                }
                EmpregaDAO empregaDAO = new EmpregaDAO();
                empregaDAO.inserir(new Emprega(dto.getCnpjEmpresa(), dto.getMatricula()), conn);
    
                // Remove tipos antigos
                try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Socio WHERE fk_funcionario_matricula = ?")) {
                    stmt1.setString(1, dto.getMatricula());
                    stmt1.executeUpdate();
                }
                try (PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Engenheiro WHERE fk_funcionario_matricula = ?")) {
                    stmt2.setString(1, dto.getMatricula());
                    stmt2.executeUpdate();
                }
                try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM OperadorDrone WHERE fk_funcionario_matricula = ?")) {
                    stmt3.setString(1, dto.getMatricula());
                    stmt3.executeUpdate();
                }
    
                // Insere novamente os tipos selecionados
                if (dto.isSocio()) {
                    try (PreparedStatement s = conn.prepareStatement("INSERT INTO Socio (fk_funcionario_matricula) VALUES (?)")) {
                        s.setString(1, dto.getMatricula());
                        s.executeUpdate();
                    }
                }
                if (dto.isEngenheiro()) {
                    try (PreparedStatement s = conn.prepareStatement("INSERT INTO Engenheiro (fk_funcionario_matricula) VALUES (?)")) {
                        s.setString(1, dto.getMatricula());
                        s.executeUpdate();
                    }
                }
                if (dto.isOperadorDrone()) {
                    try (PreparedStatement s = conn.prepareStatement("INSERT INTO OperadorDrone (fk_funcionario_matricula) VALUES (?)")) {
                        s.setString(1, dto.getMatricula());
                        s.executeUpdate();
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
    
    
}