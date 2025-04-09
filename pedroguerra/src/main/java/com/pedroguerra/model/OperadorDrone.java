package com.pedroguerra.model;

public class OperadorDrone extends Funcionario {
    public OperadorDrone(String matricula, String nome, String contato, String cidade, String numero, String bairro, String rua, String fkSupervisorMatricula) {
        super(matricula, nome, contato, cidade, numero, bairro, rua, fkSupervisorMatricula);
    }
}
