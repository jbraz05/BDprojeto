package com.pedroguerra.model;

import java.sql.Date;

public class RelatorioServico {
    private String fkServicoId;
    private float area;
    private Date dataRelatorio;
    private String observacoes;

    public RelatorioServico() {}

    public RelatorioServico(String fkServicoId, float area, Date dataRelatorio, String observacoes) {
        this.fkServicoId = fkServicoId;
        this.area = area;
        this.dataRelatorio = dataRelatorio;
        this.observacoes = observacoes;
    }

    public String getFkServicoId() { return fkServicoId; }
    public void setFkServicoId(String fkServicoId) { this.fkServicoId = fkServicoId; }

    public float getArea() { return area; }
    public void setArea(float area) { this.area = area; }

    public Date getDataRelatorio() { return dataRelatorio; }
    public void setDataRelatorio(Date dataRelatorio) { this.dataRelatorio = dataRelatorio; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
