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
}
