package com.pedroguerra.model;

public class Engenheiro extends Funcionario {
    public Engenheiro(String matricula, String nome, String contato, String cidade, String numero, String bairro, String rua, String fkSupervisorMatricula) {
        super(matricula, nome, contato, cidade, numero, bairro, rua, fkSupervisorMatricula);
    }
}

