package com.pedroguerra.model;

public class OperadorDrone extends Funcionario {
    private String fkFuncionarioMatricula;
    
    public OperadorDrone() {}
    public OperadorDrone(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }

    public String getFkFuncionarioMatricula() {
        return fkFuncionarioMatricula;
    }

    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }
}