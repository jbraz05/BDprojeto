// ServicoCadastroRequest.java
package com.pedroguerra.controller;

import com.pedroguerra.model.*;

public class ServicoCadastroRequest {
    private Cliente cliente;
    private boolean pessoaFisica;
    private Empresa empresa;
    private LocalizacaoAtuacao localizacao;
    private Funcionario engenheiro;
    private Funcionario operador;
    private Funcionario socio;
    private Servico servico;
    private RelatorioServico relatorio;
    private String notaFiscal;

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public boolean isPessoaFisica() { return pessoaFisica; }
    public void setPessoaFisica(boolean pessoaFisica) { this.pessoaFisica = pessoaFisica; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public LocalizacaoAtuacao getLocalizacao() { return localizacao; }
    public void setLocalizacao(LocalizacaoAtuacao localizacao) { this.localizacao = localizacao; }

    public Funcionario getEngenheiro() { return engenheiro; }
    public void setEngenheiro(Funcionario engenheiro) { this.engenheiro = engenheiro; }

    public Funcionario getOperador() { return operador; }
    public void setOperador(Funcionario operador) { this.operador = operador; }

    public Funcionario getSocio() { return socio; }
    public void setSocio(Funcionario socio) { this.socio = socio; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public RelatorioServico getRelatorio() { return relatorio; }
    public void setRelatorio(RelatorioServico relatorio) { this.relatorio = relatorio; }

    public String getNotaFiscal() { return notaFiscal; }
    public void setNotaFiscal(String notaFiscal) { this.notaFiscal = notaFiscal; }
}
