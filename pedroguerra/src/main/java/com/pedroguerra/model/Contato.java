package com.pedroguerra.model;

public class Contato {
    private String codigo;
    private String telefone;
    private String email;
    private String fkFuncionarioMatricula;
    private String fkEmpresaCnpj;

    public Contato() {
    }
    
    public Contato(String codigo, String telefone, String email, String fkFuncionarioMatricula, String fkEmpresaCnpj) {
        this.codigo = codigo;
        this.telefone = telefone;
        this.email = email;
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
        this.fkEmpresaCnpj = fkEmpresaCnpj;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFkFuncionarioMatricula() {
        return fkFuncionarioMatricula;
    }

    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }

    public String getFkEmpresaCnpj() {
        return fkEmpresaCnpj;
    }

    public void setFkEmpresaCnpj(String fkEmpresaCnpj) {
        this.fkEmpresaCnpj = fkEmpresaCnpj;
    }
}
