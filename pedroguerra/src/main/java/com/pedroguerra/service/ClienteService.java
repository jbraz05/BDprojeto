package com.pedroguerra.service;

import com.pedroguerra.dao.ClienteDAO;
import com.pedroguerra.dto.ClienteDTO;
import com.pedroguerra.model.Cliente;

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
        clienteDAO.remover(cnpjCpf);
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
