package com.pedroguerra.service;

import com.pedroguerra.dao.FuncionarioDAO;
import com.pedroguerra.dao.ContatoDAO;
import com.pedroguerra.model.Contato;
import com.pedroguerra.model.Funcionario;
import com.pedroguerra.dto.FuncionarioDTO;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public boolean salvarFuncionario(FuncionarioDTO dto, Contato contato) throws SQLException {
        boolean inserido = funcionarioDAO.inserir(dto);
        if (inserido && contato != null) {
            contatoDAO.inserir(contato);
        }
        return inserido;
    }

    public void atualizarFuncionario(FuncionarioDTO dto, Contato novoContato) throws SQLException {
        funcionarioDAO.atualizar(dto);
        if (novoContato != null) {
            contatoDAO.remover(novoContato.getCodigo()); // remove anterior se quiser sobrescrever
            contatoDAO.inserir(novoContato);
        }
    }

    public void removerFuncionario(String matricula) throws SQLException {
        funcionarioDAO.removerPorMatricula(matricula); // contatos são removidos via ON DELETE CASCADE
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