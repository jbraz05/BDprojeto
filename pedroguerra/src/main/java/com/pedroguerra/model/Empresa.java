package com.pedroguerra.model;

public class Empresa {
    private String cnpj;
    private String nome;
    private float capitalSocial;
    private String fk_endereco_cep;

    public Empresa() {}

    public Empresa(String cnpj, String nome, float capitalSocial, String fk_endereco_cep) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.capitalSocial = capitalSocial;
        this.fk_endereco_cep = fk_endereco_cep;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public float getCapitalSocial() { return capitalSocial; }
    public void setCapitalSocial(float capitalSocial) { this.capitalSocial = capitalSocial; }

    public String getFkEnderecoCep() { return fk_endereco_cep; }
    public void setFkEnderecoCep(String fk_endereco_cep) { this.fk_endereco_cep = fk_endereco_cep; }
}
