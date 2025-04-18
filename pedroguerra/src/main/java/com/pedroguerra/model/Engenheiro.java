package com.pedroguerra.model;

public class Engenheiro extends Funcionario {
    private String fkFuncionarioMatricula;
    public Engenheiro() {}
    public Engenheiro(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }

    public String getFkFuncionarioMatricula() {
        return fkFuncionarioMatricula;
    }

    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }
}
