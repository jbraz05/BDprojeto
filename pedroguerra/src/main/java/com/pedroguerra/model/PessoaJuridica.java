package com.pedroguerra.model;

public class PessoaJuridica extends Cliente {
    public PessoaJuridica(String cnpjCpf, String nome, String numero, String bairro, String rua) {
        super(cnpjCpf, nome, numero, bairro, rua);
    }
}