package com.pedroguerra.model;

public class Emprega {
    private String fkEmpresaCnpj;
    private String fkFuncionarioMatricula;

    public Emprega() {}

    public Emprega(String fkEmpresaCnpj, String fkFuncionarioMatricula) {
        this.fkEmpresaCnpj = fkEmpresaCnpj;
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }

    public String getFkEmpresaCnpj() {
        return fkEmpresaCnpj;
    }

    public void setFkEmpresaCnpj(String fkEmpresaCnpj) {
        this.fkEmpresaCnpj = fkEmpresaCnpj;
    }

    public String getFkFuncionarioMatricula() {
        return fkFuncionarioMatricula;
    }

    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }
}