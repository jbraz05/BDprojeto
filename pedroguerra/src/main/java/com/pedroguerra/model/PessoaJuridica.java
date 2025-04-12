package com.pedroguerra.model;

public class PessoaJuridica extends Cliente {
    public PessoaJuridica(String cnpj, String nome, String fkEnderecoCep) {
            super(cnpj, nome, fkEnderecoCep);
    }
    public String getCnpj() {
        return getCnpjCpf();
    }
}