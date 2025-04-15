package com.pedroguerra.model;

import java.util.Date;

public class RelatorioServico {
    private String fkServicoId;
    private float area;
    private Date data_relatorio;
    private String observacoes;

    public RelatorioServico(String fkServicoId, float area, Date data_relatorio, String observacoes) {
        this.fkServicoId = fkServicoId;
        this.area = area;
        this.data_relatorio = data_relatorio;
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

    public Date getDataRelatorio() {
        return data_relatorio;
    }

    public void setDataRelatorio(Date data_relatorio) {
        this.data_relatorio = data_relatorio;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
