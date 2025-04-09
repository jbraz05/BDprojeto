package com.pedroguerra.model;

public class Funcionario {

    private String matricula;
    private String nome;
    private String contato;
    private String cidade;
    private String numero;
    private String bairro;
    private String rua;
    private String fkSupervisorMatricula;

    public Funcionario() {
    }

    public Funcionario(String matricula, String nome, String contato, String cidade, String numero, String bairro, String rua, String fkSupervisorMatricula) {
        this.matricula = matricula;
        this.nome = nome;
        this.contato = contato;
        this.cidade = cidade;
        this.numero = numero;
        this.bairro = bairro;
        this.rua = rua;
        this.fkSupervisorMatricula = fkSupervisorMatricula;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getFkSupervisorMatricula() {
        return fkSupervisorMatricula;
    }

    public void setFkSupervisorMatricula(String fkSupervisorMatricula) {
        this.fkSupervisorMatricula = fkSupervisorMatricula;
    }
}

