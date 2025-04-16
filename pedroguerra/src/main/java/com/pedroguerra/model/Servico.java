package com.pedroguerra.model;

import java.sql.Date;

public class Servico {
    private String id;
    private Date data;
    private String tipo;
    private String fkFuncionarioMatricula;

    public Servico() {
    }

    public Servico(String id, Date data, String tipo, String fkFuncionarioMatricula) {
        this.id = id;
        this.data = data;
        this.tipo = tipo;
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFkFuncionarioMatricula() { return fkFuncionarioMatricula; }
    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) {
        this.fkFuncionarioMatricula = fkFuncionarioMatricula;
    }
}
