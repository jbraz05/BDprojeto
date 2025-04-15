package com.pedroguerra.model;

public class Funcionario {

    private String matricula;
    private String nome;
    private String contato;
    private String fk_endereco_cep;
    private String fkSupervisorMatricula;

    public Funcionario() {
    }

    public Funcionario(String matricula, String nome, String contato, String fk_endereco_cep, String fkSupervisorMatricula) {
        this.matricula = matricula;
        this.nome = nome;
        this.contato = contato;
        this.fkSupervisorMatricula = fkSupervisorMatricula;
        this.fk_endereco_cep = fk_endereco_cep;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getFkSupervisorMatricula() {
        return fkSupervisorMatricula;
    }

    public void setFkSupervisorMatricula(String fkSupervisorMatricula) {
        this.fkSupervisorMatricula = fkSupervisorMatricula;
    }
    public String getFkEnderecoCep() {
        return fk_endereco_cep;
    }
    public void setFkEnderecoCep(String fk_endereco_cep) {
        this.fk_endereco_cep = fk_endereco_cep;
    }
}

