package com.pedroguerra.service;

import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.dto.EmpresaDTO;
import com.pedroguerra.config.ConnectionFactory;
import com.pedroguerra.dao.ContatoDAO;
import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Contato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmpresaService {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void salvarEmpresa(Empresa empresa, Contato contato, String localizacaoCodigo) throws SQLException {
        empresaDAO.inserir(empresa, contato);

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Atua (fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo) VALUES (?, ?)")) {
            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, localizacaoCodigo);
            stmt.executeUpdate();
        }
}

    public void atualizarEmpresa(Empresa empresa, Contato novoContato) throws SQLException {
        empresaDAO.atualizar(empresa, novoContato);
    }

    public void removerEmpresa(String cnpj) throws SQLException {
        empresaDAO.removerPorCnpj(cnpj);
    }

    
    public EmpresaDTO buscarEmpresa(String cnpj) throws SQLException {
        return empresaDAO.buscarPorCnpj(cnpj);
    }

    public List<Empresa> listarTodas() throws SQLException {
        return empresaDAO.listarTodas();
    }

    public List<EmpresaDTO> listarTodasComLocalizacao() throws SQLException {
        return empresaDAO.listarTodasComLocalizacao();
    }

    public List<Contato> listarContatos(String cnpj) throws SQLException {
        return contatoDAO.listarPorEmpresa(cnpj);
    }
}
