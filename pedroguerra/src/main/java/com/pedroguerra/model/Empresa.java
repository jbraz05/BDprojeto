package com.pedroguerra.model;

public class Empresa {
    private String cnpj;
    private String nome;
    private String fk_endereco_cep;
    public String getCnpj() {
        return cnpj;
    }
    public Empresa() {
    }
    public Empresa(String cnpj, String nome, String fk_endereco_cep) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.fk_endereco_cep = fk_endereco_cep;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getFkEnderecoCep() {
        return fk_endereco_cep;
    }
    public void setFkEnderecoCep(String fk_endereco_cep) {
        this.fk_endereco_cep = fk_endereco_cep;
    }

}