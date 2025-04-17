package com.pedroguerra.service;

import com.pedroguerra.dao.FuncionarioDAO;
import com.pedroguerra.dto.FuncionarioDTO;
import com.pedroguerra.model.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO dao = new FuncionarioDAO();

    public void salvarFuncionario(FuncionarioDTO dto) throws SQLException {
        dao.inserir(dto);
    }

    public void removerFuncionario(String matricula) throws SQLException {
        dao.removerPorMatricula(matricula);
    }

    public List<Funcionario> listarFuncionariosPorEmpresa(String cnpj) throws SQLException {
        return dao.listarPorEmpresa(cnpj);
    }

    public List<Funcionario> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public FuncionarioDTO buscarFuncionarioParaEdicao(String matricula) throws SQLException {
        return dao.buscarFuncionarioDTO(matricula);
    }
    public boolean funcionarioExiste(String matricula) throws SQLException {
        return dao.buscarFuncionarioDTO(matricula) != null;
    }
    
    public void atualizarFuncionario(FuncionarioDTO dto) throws SQLException {
        dao.atualizar(dto);
    }
    public List<FuncionarioDTO> listarTodosDTO() throws SQLException {
        return dao.listarTodosDTO();
    }
    
    public List<FuncionarioDTO> listarFuncionariosPorEmpresaDTO(String cnpj) throws SQLException {
        return dao.listarPorEmpresaDTO(cnpj);
    }

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public List<Funcionario> listarOperadoresDrone() throws SQLException {
        return funcionarioDAO.listarOperadoresDrone();
    }
    
}
