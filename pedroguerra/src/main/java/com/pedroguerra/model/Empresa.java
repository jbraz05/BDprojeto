package com.pedroguerra.model;

public class Empresa {
    private String cnpj;
    private String nome;
    private String contato;
    private String fk_endereco_cep;
    public String getCnpj() {
        return cnpj;
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
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
    public String getFkEnderecoCep() {
        return fk_endereco_cep;
    }
    public void setFkEnderecoCep(String fk_endereco_cep) {
        this.fk_endereco_cep = fk_endereco_cep;
    }

}