package com.pedroguerra.service;

import com.pedroguerra.dao.AtuaDAO;
import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dao.EnderecoDAO;
import com.pedroguerra.dto.EmpresaDTO;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Endereco;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final AtuaDAO atuaDAO = new AtuaDAO();

    public void processarCadastro(Empresa empresa, String cep, String rua, String numero, String bairro, String cidade, String codigoLocalizacao) throws SQLException {
        Endereco endereco = new Endereco(cep, numero, cidade, bairro, rua);
        enderecoDAO.inserir(endereco);

        empresa.setFkEnderecoCep(cep);
        empresaDAO.inserir(empresa);

        atuaDAO.inserir(empresa.getCnpj(), codigoLocalizacao);
    }

    public void removerEmpresa(String cnpj) throws SQLException {
        empresaDAO.removerPorCnpj(cnpj);
    }

    public Empresa buscarEmpresaPorCnpj(String cnpj) throws SQLException {
        return empresaDAO.buscarPorCnpj(cnpj);
    }

    public void atualizarEmpresaComEndereco(Empresa empresa, String cep, String rua, String numero, String bairro, String cidade, String codigoLocalizacao) throws SQLException {
        Endereco endereco = new Endereco(cep, numero, cidade, bairro, rua);

        if (!enderecoDAO.cepExiste(cep)) {
            enderecoDAO.inserir(endereco);
        } else {
            enderecoDAO.atualizar(endereco);
        }

        empresa.setFkEnderecoCep(cep);
        empresaDAO.atualizar(empresa);
        atuaDAO.atualizar(empresa.getCnpj(), codigoLocalizacao);
    }

    public List<EmpresaDTO> listarEmpresasComEnderecoEAtuacao() throws SQLException {
        return empresaDAO.listarTodasComLocalizacao();
    }
}
