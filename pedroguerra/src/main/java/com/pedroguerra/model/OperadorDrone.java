package com.pedroguerra.model;

public class OperadorDrone extends Funcionario {
    public OperadorDrone(String matricula, String nome, String contato,String fk_endereco_cep, String fk_supervisor_matricula) {
        super(matricula, nome, contato, fk_endereco_cep ,fk_supervisor_matricula);
    }
}
