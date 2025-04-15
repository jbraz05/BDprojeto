package com.pedroguerra.model;

public class Endereco {
    private String cep;
    private String numero;
    private String cidade;
    private String bairro;
    private String rua;

    public Endereco() {
    }

    public Endereco(String cep, String numero, String cidade, String bairro, String rua) {
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
}
