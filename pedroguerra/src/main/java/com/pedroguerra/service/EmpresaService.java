package com.pedroguerra.service;

import com.pedroguerra.dao.AtuaDAO;
import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dao.EnderecoDAO;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Endereco;

import java.sql.SQLException;
import java.util.List;

public class EmpresaService {

    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final AtuaDAO atuaDAO = new AtuaDAO();

    public void cadastrarEmpresa(Empresa empresa, Endereco endereco, String codigoLocalizacao) throws SQLException {
        if (enderecoDAO.buscarPorCep(endereco.getCep()) == null) {
            enderecoDAO.inserir(endereco);
        }

        empresa.setFkEnderecoCep(endereco.getCep());
        empresaDAO.inserir(empresa);
        atuaDAO.inserir(empresa.getCnpj(), codigoLocalizacao);
    }

    public List<String[]> listarEmpresasComEnderecoEAtuacao() throws SQLException {
        return empresaDAO.listarTodasComLocalizacao();
    }

    public Empresa buscarEmpresaPorCnpj(String cnpj) throws SQLException {
        return empresaDAO.buscarPorCnpj(cnpj);
    }

    public void atualizarEmpresa(Empresa empresa) throws SQLException {
        empresaDAO.atualizar(empresa);
    }

    public void removerEmpresa(String cnpj) throws SQLException {
        empresaDAO.removerPorCnpj(cnpj);
    }

}
