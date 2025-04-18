package com.pedroguerra.model;

public class Socio  extends Funcionario {
    private String fkFuncionarioMatricula;

    public Socio() {}
    public Socio(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }

    public String getFkFuncionarioMatricula() {
        return fkFuncionarioMatricula;
    }

    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }
}