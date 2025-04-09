// ServicoController.java
package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;



@RestController
@RequestMapping("/servico")
public class ServicoController {

    @PostMapping("/cadastrar")
    public String cadastrarServico(@RequestBody ServicoCadastroRequest req) {
        try {
            EmpresaDAO empresaDAO = new EmpresaDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            EngenheiroDAO engenheiroDAO = new EngenheiroDAO();
            OperadorDroneDAO operadorDroneDAO = new OperadorDroneDAO();
            SocioDAO socioDAO = new SocioDAO();
            ServicoDAO servicoDAO = new ServicoDAO();
            MapeamentoTradicionalDAO mapDAO = new MapeamentoTradicionalDAO();
            VooPanoramicoDAO vooDAO = new VooPanoramicoDAO();
            RelatorioServicoDAO relatorioDAO = new RelatorioServicoDAO();
            LocalizacaoAtuacaoDAO localDAO = new LocalizacaoAtuacaoDAO();
            AtuaDAO atuaDAO = new AtuaDAO();
            EmpregaDAO empregaDAO = new EmpregaDAO();
            PossuiDAO possuiDAO = new PossuiDAO();
            ContrataDAO contrataDAO = new ContrataDAO();

            // Empresa
            empresaDAO.inserir(req.getEmpresa());

            clienteDAO.inserir(req.getCliente());
            if (req.isPessoaFisica()) {
                PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
                pfDAO.inserir(req.getCliente().getCnpjCpf());
            } else {
                PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();
                pjDAO.inserir(req.getCliente().getCnpjCpf());
            }

            // Localização
            localDAO.inserir(req.getLocalizacao());
            atuaDAO.inserir(req.getEmpresa().getCnpj(), req.getLocalizacao().getCodigo());

            // Funcionários
            funcionarioDAO.inserir(req.getEngenheiro());
            engenheiroDAO.inserir(req.getEngenheiro().getMatricula());

            funcionarioDAO.inserir(req.getOperador());
            operadorDroneDAO.inserir(req.getOperador().getMatricula());

            funcionarioDAO.inserir(req.getSocio());
            socioDAO.inserir(req.getSocio().getMatricula());

            empregaDAO.inserir(req.getEmpresa().getCnpj(), req.getEngenheiro().getMatricula());
            empregaDAO.inserir(req.getEmpresa().getCnpj(), req.getOperador().getMatricula());
            empregaDAO.inserir(req.getEmpresa().getCnpj(), req.getSocio().getMatricula());

            // Serviço
            servicoDAO.inserir(req.getServico());

            if (req.getServico().getTipo().equalsIgnoreCase("mapeamento")) {
                mapDAO.inserir(req.getServico().getId());
            } else if (req.getServico().getTipo().equalsIgnoreCase("voo")) {
                vooDAO.inserir(req.getServico().getId(), req.getOperador().getMatricula());
            }

            // Relatório
            relatorioDAO.inserir(req.getRelatorio());

            // Possui
            possuiDAO.inserir(req.getServico().getId(), req.getLocalizacao().getCodigo(), req.getEmpresa().getCnpj());

            // Contrato (opcional)
            contrataDAO.inserir(req.getNotaFiscal(), req.getCliente().getCnpjCpf());

            return "Serviço e dados relacionados cadastrados com sucesso!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar serviço: " + e.getMessage();
        }
    }
}
