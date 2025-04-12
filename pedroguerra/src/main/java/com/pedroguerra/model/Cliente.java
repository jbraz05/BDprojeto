package com.pedroguerra.model;

public class Cliente {
    private String cnpjCpf;
    private String nome;
    private String fkEnderecoCep;

    public Cliente(String cnpjCpf, String nome, String fkEnderecoCep) {
        this.cnpjCpf = cnpjCpf;
        this.nome = nome;
        this.fkEnderecoCep = fkEnderecoCep;
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

    public String getFkEnderecoCep() {
        return fkEnderecoCep;
    }

    public void setFkEnderecoCep(String fkEnderecoCep) {
        this.fkEnderecoCep = fkEnderecoCep;
    }
}
