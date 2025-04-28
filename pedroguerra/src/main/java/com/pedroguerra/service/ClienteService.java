package com.pedroguerra.service;

import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.ClienteDAO;
import com.pedroguerra.dto.ClienteDTO;
import com.pedroguerra.model.Cliente;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void salvarCliente(ClienteDTO dto) throws SQLException {
        clienteDAO.inserir(dto);
    }

    public boolean atualizarCliente(ClienteDTO dto) throws SQLException {
        return clienteDAO.atualizar(dto);
    }

    public void removerCliente(String cnpjCpf) throws SQLException {
    try (Connection conn = ConnectionFactory.getConnection()) {
        conn.setAutoCommit(false);

        try {
            // 1. Buscar todos os IDs dos serviços ligados a esse cliente
            List<String> servicos = clienteDAO.buscarIdsServicosDoCliente(cnpjCpf);

            for (String idServico : servicos) {
                // 2. Remover o vínculo com o cliente
                String sqlContrata = "DELETE FROM Contrata WHERE nota_fiscal = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlContrata)) {
                    stmt.setString(1, idServico);
                    stmt.executeUpdate();
                }

                // 3. Verificar se é Voo Panorâmico
                String tipoServico = clienteDAO.buscarTipoServico(idServico);
                if ("voo panorâmico".equalsIgnoreCase(tipoServico)) {
                    // 4. Se for, desfazer também o VooPanoramico
                    String sqlVoo = "DELETE FROM VooPanoramico WHERE fk_Servico_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sqlVoo)) {
                        stmt.setString(1, idServico);
                        stmt.executeUpdate();
                    }
                }
            }

            // 5. Agora pode remover o cliente
            clienteDAO.removerPorConexao(cnpjCpf, conn);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }
}


    public List<ClienteDTO> listarTodos() throws SQLException {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarClientePorCnpjCpf(String cnpjCpf) throws SQLException {
        return clienteDAO.buscarPorCnpjCpf(cnpjCpf);
    }

    public boolean clienteExiste(String cnpjCpf) throws SQLException {
        return clienteDAO.buscarPorCnpjCpf(cnpjCpf) != null;
    }
}
