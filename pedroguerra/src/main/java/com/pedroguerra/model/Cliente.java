package com.pedroguerra.model;

public class Cliente {

    private String cnpjCpf;
    private String nome;
    private String numero;
    private String bairro;
    private String rua;

    public Cliente(String cnpjCpf, String nome, String numero, String bairro, String rua) {
        this.cnpjCpf = cnpjCpf;
        this.nome = nome;
        this.numero = numero;
        this.bairro = bairro;
        this.rua = rua;
    }
    
    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
