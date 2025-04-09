package com.pedroguerra.model;

import java.util.Date;

public class RelatorioServico {
    private String fkServicoId;
    private float area;
    private Date data;
    private String observacoes;

    public RelatorioServico(String fkServicoId, float area, Date data, String observacoes) {
        this.fkServicoId = fkServicoId;
        this.area = area;
        this.data = data;
        this.observacoes = observacoes;
    }

    public String getfkServicoId() {
        return fkServicoId;
    }

    public void setfkServicoId(String fkServicoId) {
        this.fkServicoId = fkServicoId;
    }

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
