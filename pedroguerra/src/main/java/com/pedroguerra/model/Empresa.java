package com.pedroguerra.model;

import java.math.BigDecimal;

public class Empresa {
    private String cnpj;
    private String nome;
    private BigDecimal capitalSocial;
    private String fk_endereco_cep;

    public Empresa() {}

    public Empresa(String cnpj, String nome, BigDecimal capitalSocial, String fk_endereco_cep) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.capitalSocial = capitalSocial;
        this.fk_endereco_cep = fk_endereco_cep;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getCapitalSocial() { return capitalSocial; }
    public void setCapitalSocial(BigDecimal capitalSocial) { this.capitalSocial = capitalSocial; }

    public String getFkEnderecoCep() { return fk_endereco_cep; }
    public void setFkEnderecoCep(String fk_endereco_cep) { this.fk_endereco_cep = fk_endereco_cep; }
}
