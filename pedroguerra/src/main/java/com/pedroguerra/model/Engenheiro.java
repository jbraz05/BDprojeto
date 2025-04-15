package com.pedroguerra.model;

public class Engenheiro extends Funcionario {
    public Engenheiro(String matricula, String nome, String contato, String cidade,String fk_endereco_cep, String fk_supervisor_matricula) {
        super(matricula, nome, contato, fk_supervisor_matricula, fk_supervisor_matricula);
    }
}

