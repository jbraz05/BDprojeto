package com.pedroguerra.controller;

import com.pedroguerra.dao.*;
import com.pedroguerra.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ServicoController {

    @GetMapping("/")
    public String mostrarHome() {
        return "home";
    }

    @GetMapping("/empresa")
    public String mostrarEmpresa() {
        return "empresa";
    }

    @PostMapping("/empresa/cadastrar")
    @ResponseBody
    public String cadastrarEmpresa(@RequestBody Empresa empresa) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            dao.inserir(empresa);
            return "Empresa cadastrada com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar empresa: " + e.getMessage();
        }
    }

    @GetMapping("/empresa/listar")
    public String listarEmpresas(Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            List<Empresa> empresas = dao.listarTodas();
            model.addAttribute("empresas", empresas);
            return "lista-empresas";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao listar empresas: " + e.getMessage());
            return "erro";
        }
    }


    @PostMapping("/empresa/remover")
    public String removerEmpresa(@RequestParam String cnpj, Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            dao.removerPorCnpj(cnpj);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao remover empresa: " + e.getMessage());
            return "erro";
        }
    }


    @PostMapping("/empresa/atualizar")
    public String atualizarEmpresa(@ModelAttribute Empresa empresa, Model model) {
        try {
            EmpresaDAO dao = new EmpresaDAO();
            dao.atualizar(empresa);
            return "redirect:/empresa/listar";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao atualizar empresa: " + e.getMessage());
            return "erro";
        }
    }
    
@GetMapping("/empresa/editar")
public String mostrarFormularioEdicao(@RequestParam String cnpj, Model model) {
    try {
        EmpresaDAO dao = new EmpresaDAO();
        Empresa empresa = dao.buscarPorCnpj(cnpj);
        model.addAttribute("empresa", empresa);
        return "editar-empresa"; // Nome do HTML que vamos criar
    } catch (SQLException e) {
        e.printStackTrace();
        model.addAttribute("erro", "Erro ao buscar empresa: " + e.getMessage());
        return "erro";
    }
}



    @GetMapping("/cliente")
    public String mostrarCliente() {
        return "cliente";
    }

    @PostMapping("/cliente/cadastrar/pf")
    @ResponseBody
    public String cadastrarClientePF(@RequestBody Cliente cliente) {
        try {
            ClienteDAO dao = new ClienteDAO();
            dao.inserir(cliente);
            PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
            pfDAO.inserir(cliente.getCnpjCpf());
            return "Pessoa Física cadastrada com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar PF: " + e.getMessage();
        }
    }

    @PostMapping("/cliente/cadastrar/pj")
    @ResponseBody
    public String cadastrarClientePJ(@RequestBody Cliente cliente) {
        try {
            ClienteDAO dao = new ClienteDAO();
            dao.inserir(cliente);
            PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();
            pjDAO.inserir(cliente.getCnpjCpf());
            return "Pessoa Jurídica cadastrada com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar PJ: " + e.getMessage();
        }
    }

    @GetMapping("/funcionario")
    public String mostrarFuncionario() {
        return "funcionario";
    }

    @PostMapping("/funcionario/cadastrar")
    @ResponseBody
    public String cadastrarFuncionario(@RequestBody Funcionario funcionario) {
        try {
            FuncionarioDAO dao = new FuncionarioDAO();
            dao.inserir(funcionario);
            return "Funcionário cadastrado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar funcionário: " + e.getMessage();
        }
    }

    @PostMapping("/engenheiro/cadastrar")
    @ResponseBody
    public String cadastrarEngenheiro(@RequestBody Funcionario funcionario) {
        try {
            EngenheiroDAO dao = new EngenheiroDAO();
            dao.inserir(funcionario.getMatricula());
            return "Engenheiro vinculado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar engenheiro: " + e.getMessage();
        }
    }

    @PostMapping("/operador/cadastrar")
    @ResponseBody
    public String cadastrarOperador(@RequestBody Funcionario funcionario) {
        try {
            OperadorDroneDAO dao = new OperadorDroneDAO();
            dao.inserir(funcionario.getMatricula());
            return "Operador de Drone vinculado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar operador: " + e.getMessage();
        }
    }

    @PostMapping("/socio/cadastrar")
    @ResponseBody
    public String cadastrarSocio(@RequestBody Funcionario funcionario) {
        try {
            SocioDAO dao = new SocioDAO();
            dao.inserir(funcionario.getMatricula());
            return "Sócio vinculado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar sócio: " + e.getMessage();
        }
    }

    @GetMapping("/localizacao")
    public String mostrarLocalizacao() {
        return "localizacao";
    }

    @PostMapping("/localizacao/cadastrar")
    @ResponseBody
    public String cadastrarLocalizacao(@RequestBody LocalizacaoAtuacao local) {
        try {
            LocalizacaoAtuacaoDAO dao = new LocalizacaoAtuacaoDAO();
            dao.inserir(local);
            return "Localização cadastrada com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar localização: " + e.getMessage();
        }
    }

    @GetMapping("/servico")
    public String mostrarFormularioServico() {
        return "servico";
    }

    @PostMapping("/servico/cadastrar")
    @ResponseBody
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

            empresaDAO.inserir(req.getEmpresa());
            clienteDAO.inserir(req.getCliente());
            if (req.isPessoaFisica()) {
                PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
                pfDAO.inserir(req.getCliente().getCnpjCpf());
            } else {
                PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();
                pjDAO.inserir(req.getCliente().getCnpjCpf());
            }

            localDAO.inserir(req.getLocalizacao());
            atuaDAO.inserir(req.getEmpresa().getCnpj(), req.getLocalizacao().getCodigo());

            funcionarioDAO.inserir(req.getEngenheiro());
            engenheiroDAO.inserir(req.getEngenheiro().getMatricula());

            funcionarioDAO.inserir(req.getOperador());
            operadorDroneDAO.inserir(req.getOperador().getMatricula());

            funcionarioDAO.inserir(req.getSocio());
            socioDAO.inserir(req.getSocio().getMatricula());

            empregaDAO.inserir(req.getEmpresa().getCnpj(), req.getEngenheiro().getMatricula());
            empregaDAO.inserir(req.getEmpresa().getCnpj(), req.getOperador().getMatricula());
            empregaDAO.inserir(req.getEmpresa().getCnpj(), req.getSocio().getMatricula());

            servicoDAO.inserir(req.getServico());
            if (req.getServico().getTipo().equalsIgnoreCase("mapeamento")) {
                mapDAO.inserir(req.getServico().getId());
            } else if (req.getServico().getTipo().equalsIgnoreCase("voo")) {
                vooDAO.inserir(req.getServico().getId(), req.getOperador().getMatricula());
            }

            relatorioDAO.inserir(req.getRelatorio());
            possuiDAO.inserir(req.getServico().getId(), req.getLocalizacao().getCodigo(), req.getEmpresa().getCnpj());
            contrataDAO.inserir(req.getNotaFiscal(), req.getCliente().getCnpjCpf());

            return "Serviço e dados relacionados cadastrados com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cadastrar serviço: " + e.getMessage();
        }
    }
}