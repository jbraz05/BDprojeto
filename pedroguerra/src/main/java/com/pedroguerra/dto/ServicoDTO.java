package com.pedroguerra.dto;

import java.sql.Date;

public class ServicoDTO {
    private String id;
    private Date data;
    private String tipo;
    private String fkFuncionarioMatricula;
    private String nomeFuncionario;

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

    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
}
