package com.pedroguerra.dao;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dto.FuncionarioDTO;
import com.pedroguerra.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private final ContatoDAO contatoDAO = new ContatoDAO();

    public boolean inserir(FuncionarioDTO dto) throws SQLException {
        String sql = "INSERT INTO Funcionario (matricula, nome, fk_endereco_cep, fk_supervisor_matricula) VALUES (?, ?, ?, ?)";
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

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, dto.getMatricula());
                    stmt.setString(2, dto.getNome());
                    stmt.setString(3, dto.getFkEnderecoCep());
                    if (dto.getFkSupervisorMatricula() == null || dto.getFkSupervisorMatricula().isEmpty()) {
                        stmt.setNull(4, Types.VARCHAR);
                    } else {
                        stmt.setString(4, dto.getFkSupervisorMatricula());
                    }
                    stmt.executeUpdate();
                }

                new EmpregaDAO().inserir(new Emprega(dto.getCnpjEmpresa(), dto.getMatricula()), conn);

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

                if (dto.getEmail() != null || dto.getTelefone() != null) {
                    contatoDAO.inserir(new Contato("CTF_" + dto.getMatricula(), dto.getTelefone(), dto.getEmail(), dto.getMatricula(), null), conn);
                }

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

    public void atualizar(FuncionarioDTO dto) throws SQLException {
        String sql = "UPDATE Funcionario SET nome = ?, fk_endereco_cep = ?, fk_supervisor_matricula = ? WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                if (!enderecoDAO.cepExiste(dto.getFkEnderecoCep())) {
                    enderecoDAO.inserir(new Endereco(
                        dto.getFkEnderecoCep(),
                        dto.getNumeroEndereco(),
                        dto.getCidadeEndereco(),
                        dto.getBairroEndereco(),
                        dto.getRuaEndereco()
                    ));
                }

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, dto.getNome());
                    stmt.setString(2, dto.getFkEnderecoCep());
                    if (dto.getFkSupervisorMatricula() == null || dto.getFkSupervisorMatricula().isBlank()) {
                        stmt.setNull(3, Types.VARCHAR);
                    } else {
                        stmt.setString(3, dto.getFkSupervisorMatricula());
                    }
                    stmt.setString(4, dto.getMatricula());
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Emprega WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, dto.getMatricula());
                    stmt.executeUpdate();
                }
                new EmpregaDAO().inserir(new Emprega(dto.getCnpjEmpresa(), dto.getMatricula()), conn);

                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Socio WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, dto.getMatricula());
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Engenheiro WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, dto.getMatricula());
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM OperadorDrone WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, dto.getMatricula());
                    stmt.executeUpdate();
                }

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

                if (dto.getEmail() != null || dto.getTelefone() != null) {
                    contatoDAO.inserir(new Contato("CTF_" + dto.getMatricula(), dto.getTelefone(), dto.getEmail(), dto.getMatricula(), null), conn);
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

    public FuncionarioDTO buscarFuncionarioDTO(String matricula) throws SQLException {
        String sql = "SELECT f.*, e.rua, e.numero, e.cidade, e.bairro " +
                     "FROM Funcionario f " +
                     "JOIN Endereco e ON f.fk_endereco_cep = e.cep " +
                     "WHERE f.matricula = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FuncionarioDTO dto = new FuncionarioDTO();
                dto.setMatricula(rs.getString("matricula"));
                dto.setNome(rs.getString("nome"));
                dto.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                dto.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
                dto.setRuaEndereco(rs.getString("rua"));
                dto.setNumeroEndereco(rs.getString("numero"));
                dto.setCidadeEndereco(rs.getString("cidade"));
                dto.setBairroEndereco(rs.getString("bairro"));

                // Contato
                List<Contato> contatos = contatoDAO.listarPorFuncionario(matricula);
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }

                // Especializações
                dto.setSocio(registroExiste(conn, "Socio", matricula));
                dto.setEngenheiro(registroExiste(conn, "Engenheiro", matricula));
                dto.setOperadorDrone(registroExiste(conn, "OperadorDrone", matricula));

                // Empresa vinculada
                String sqlEmpresa = "SELECT fk_Empresa_cnpj FROM Emprega WHERE fk_funcionario_matricula = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlEmpresa)) {
                    ps.setString(1, matricula);
                    try (ResultSet rse = ps.executeQuery()) {
                        if (rse.next()) {
                            dto.setCnpjEmpresa(rse.getString("fk_Empresa_cnpj"));
                        }
                    }
                }

                return dto;
            }
        }
        return null;
    }

    public List<FuncionarioDTO> listarTodosDTO() throws SQLException {
        List<FuncionarioDTO> lista = new ArrayList<>();
        String sql = "SELECT f.*, e.rua, e.numero, e.cidade, e.bairro, emp.fk_Empresa_cnpj FROM Funcionario f JOIN Endereco e ON f.fk_endereco_cep = e.cep JOIN Emprega emp ON f.matricula = emp.fk_funcionario_matricula";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FuncionarioDTO dto = new FuncionarioDTO();
                dto.setMatricula(rs.getString("matricula"));
                dto.setNome(rs.getString("nome"));
                dto.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                dto.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
                dto.setRuaEndereco(rs.getString("rua"));
                dto.setNumeroEndereco(rs.getString("numero"));
                dto.setCidadeEndereco(rs.getString("cidade"));
                dto.setBairroEndereco(rs.getString("bairro"));
                dto.setCnpjEmpresa(rs.getString("fk_Empresa_cnpj"));
                dto.setSocio(registroExiste(conn, "Socio", dto.getMatricula()));
                dto.setEngenheiro(registroExiste(conn, "Engenheiro", dto.getMatricula()));
                dto.setOperadorDrone(registroExiste(conn, "OperadorDrone", dto.getMatricula()));
                List<Contato> contatos = contatoDAO.listarPorFuncionario(dto.getMatricula());
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }
                lista.add(dto);
            }
        }
        return lista;
    }

    public void removerPorMatricula(String matricula) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. Buscar todos os serviços do funcionário
                List<String> idsServicos = new ArrayList<>();
                try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Servico WHERE fk_Funcionario_matricula = ?")) {
                    stmt.setString(1, matricula);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        idsServicos.add(rs.getString("id"));
                    }
                }
    
                // 2. Apagar registros dependentes de cada serviço
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
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Possui WHERE fk_Servico_id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Servico WHERE id = ?")) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                }
    
                // 3. Desvincula subordinados
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE Funcionario SET fk_supervisor_matricula = NULL WHERE fk_supervisor_matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
                }
    
                // 4. Apaga especializações
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Socio WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Engenheiro WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM OperadorDrone WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
                }
    
                // 5. Apaga vínculo com empresa
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Emprega WHERE fk_funcionario_matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
                }
    
                // 6. Apaga funcionário
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Funcionario WHERE matricula = ?")) {
                    stmt.setString(1, matricula);
                    stmt.executeUpdate();
                }
    
                // 7. Apaga contato
                contatoDAO.remover("CTF_" + matricula, conn);
    
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
        String sql = "SELECT f.* FROM Funcionario f JOIN Emprega e ON f.matricula = e.fk_funcionario_matricula WHERE e.fk_Empresa_cnpj = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setMatricula(rs.getString("matricula"));
                f.setNome(rs.getString("nome"));
                f.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                f.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
                lista.add(f);
            }
        }
        return lista;
    }

    public List<FuncionarioDTO> listarPorEmpresaDTO(String cnpj) throws SQLException {
        List<FuncionarioDTO> lista = new ArrayList<>();
        String sql = "SELECT f.*, e.rua, e.numero, e.cidade, e.bairro, emp.fk_Empresa_cnpj " +
                     "FROM Funcionario f " +
                     "JOIN Endereco e ON f.fk_endereco_cep = e.cep " +
                     "JOIN Emprega emp ON f.matricula = emp.fk_funcionario_matricula " +
                     "WHERE emp.fk_Empresa_cnpj = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                FuncionarioDTO dto = new FuncionarioDTO();
                dto.setMatricula(rs.getString("matricula"));
                dto.setNome(rs.getString("nome"));
                dto.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                dto.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
                dto.setRuaEndereco(rs.getString("rua"));
                dto.setNumeroEndereco(rs.getString("numero"));
                dto.setCidadeEndereco(rs.getString("cidade"));
                dto.setBairroEndereco(rs.getString("bairro"));
                dto.setCnpjEmpresa(rs.getString("fk_Empresa_cnpj"));

                dto.setSocio(registroExiste(conn, "Socio", dto.getMatricula()));
                dto.setEngenheiro(registroExiste(conn, "Engenheiro", dto.getMatricula()));
                dto.setOperadorDrone(registroExiste(conn, "OperadorDrone", dto.getMatricula()));

                List<Contato> contatos = contatoDAO.listarPorFuncionario(dto.getMatricula());
                for (Contato c : contatos) {
                    dto.setEmail(c.getEmail());
                    dto.setTelefone(c.getTelefone());
                }

                lista.add(dto);
            }
        }

        return lista;
    }

    private boolean registroExiste(Connection conn, String tabela, String matricula) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM " + tabela + " WHERE fk_funcionario_matricula = ? LIMIT 1")) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Funcionario> listarOperadoresDrone() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.* FROM Funcionario f " +
                     "JOIN OperadorDrone o ON f.matricula = o.fk_Funcionario_matricula";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setMatricula(rs.getString("matricula"));
                f.setNome(rs.getString("nome"));
                f.setFkEnderecoCep(rs.getString("fk_endereco_cep"));
                f.setFkSupervisorMatricula(rs.getString("fk_supervisor_matricula"));
                lista.add(f);
            }
        }

        return lista;
    }

    public Funcionario buscarFuncionarioSimples(String matricula) throws SQLException {
        if (matricula == null || matricula.isBlank()) return null;
        String sql = "SELECT * FROM Funcionario WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Funcionario f = new Funcionario();
                f.setMatricula(rs.getString("matricula"));
                f.setNome(rs.getString("nome"));
                return f;
            }
        }
        return null;
    }
}
