package com.pedroguerra.model;

import java.util.Date;

public class RelatorioServico {
    private float area;
    private Date data;
    private String observacoes;
    public float getArea() {
        return area;
    }
    public void setArea(float area) {
        this.area = area;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
}