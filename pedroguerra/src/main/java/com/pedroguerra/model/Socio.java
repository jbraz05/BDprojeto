package com.pedroguerra.model;

public class Socio extends Funcionario {
    public Socio(String matricula, String nome, String contato,String fk_endereco_cep ,String fk_supervisor_matricula) {
        super(matricula, nome, contato, fk_endereco_cep, fk_supervisor_matricula);
    }
}

