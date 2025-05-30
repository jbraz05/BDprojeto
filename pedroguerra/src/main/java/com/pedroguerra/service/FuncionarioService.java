package com.pedroguerra.service;

import com.pedroguerra.dao.FuncionarioDAO;
import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.ContatoDAO;
import com.pedroguerra.model.Contato;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.dto.FuncionarioDTO;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public boolean salvarFuncionario(FuncionarioDTO dto, Contato contato) throws SQLException {
        return funcionarioDAO.inserir(dto);
    }

    public void atualizarFuncionario(FuncionarioDTO dto, Contato novoContato) throws SQLException {
        funcionarioDAO.atualizar(dto);
        if (novoContato != null) {
            contatoDAO.remover(novoContato.getCodigo());
            contatoDAO.inserir(novoContato);
        }
    }

    public void removerFuncionario(String matricula) throws SQLException {
    try (Connection conn = ConnectionFactory.getConnection()) {
        funcionarioDAO.removerPorMatriculaComConexao(matricula, conn);
    }
}


    public List<FuncionarioDTO> listarTodos() throws SQLException {
        return funcionarioDAO.listarTodosDTO();
    }

    public FuncionarioDTO buscarFuncionarioDTO(String matricula) throws SQLException {
        return funcionarioDAO.buscarFuncionarioDTO(matricula);
    }

    public List<FuncionarioDTO> listarFuncionariosPorEmpresaDTO(String cnpj) throws SQLException {
        return funcionarioDAO.listarPorEmpresaDTO(cnpj);
    }

    public List<Funcionario> listarOperadoresDrone() throws SQLException {
        return funcionarioDAO.listarOperadoresDrone();
    }
}
