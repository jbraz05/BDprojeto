package com.pedroguerra.model;

public class PessoaFisica extends Cliente {
    public PessoaFisica(String cpf, String nome, String fkEnderecoCep) {
        super(cpf, nome, fkEnderecoCep);
    }
    public String getCpf() {
        return getCnpjCpf();
    }
}