package com.pedroguerra.model;

public class Emprega{
    private String empresaCnpj;
    private String funcionarioMatricula;

    public Emprega(String empresaCnpj, String funcionarioMatricula) {
        this.empresaCnpj = empresaCnpj;
        this.funcionarioMatricula = funcionarioMatricula;
    }

    public String getEmpresaCnpj(){
        return empresaCnpj;
    }
    
    public void setEmpresaCnpj(String empresaCnpj){
        this.empresaCnpj = empresaCnpj;
    }

    public String getFuncionarioMatricula(){
        return funcionarioMatricula;
    }

    public void setFuncionarioMatricula(String funcionarioMatricula){
        this.funcionarioMatricula = funcionarioMatricula;
    }
}