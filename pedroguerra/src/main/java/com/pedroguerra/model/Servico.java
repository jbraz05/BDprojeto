package com.pedroguerra.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Servico {
    private String id;
    private Date data;
    private String tipo;
    private String fkFuncionarioMatricula;
    private boolean feito;

    private int periodoPreparatorio;
    private int periodoTrabalhoCampo;
    private int periodoTrabalhoEscritorio;
    private Date prazoTrabalho;
    private BigDecimal valorMedicao;
    private Date dataEmissaoMedicao;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFkFuncionarioMatricula() { return fkFuncionarioMatricula; }
    public void setFkFuncionarioMatricula(String fkFuncionarioMatricula) { this.fkFuncionarioMatricula = fkFuncionarioMatricula; }

    public boolean isFeito() { return feito; }
    public void setFeito(boolean feito) { this.feito = feito; }

    public int getPeriodoPreparatorio() { return periodoPreparatorio; }
    public void setPeriodoPreparatorio(int periodoPreparatorio) { this.periodoPreparatorio = periodoPreparatorio; }

    public int getPeriodoTrabalhoCampo() { return periodoTrabalhoCampo; }
    public void setPeriodoTrabalhoCampo(int periodoTrabalhoCampo) { this.periodoTrabalhoCampo = periodoTrabalhoCampo; }

    public int getPeriodoTrabalhoEscritorio() { return periodoTrabalhoEscritorio; }
    public void setPeriodoTrabalhoEscritorio(int periodoTrabalhoEscritorio) { this.periodoTrabalhoEscritorio = periodoTrabalhoEscritorio; }

    public Date getPrazoTrabalho() { return prazoTrabalho; }
    public void setPrazoTrabalho(Date prazoTrabalho) { this.prazoTrabalho = prazoTrabalho; }

    public BigDecimal getValorMedicao() { return valorMedicao; }
    public void setValorMedicao(BigDecimal valorMedicao) { this.valorMedicao = valorMedicao; }

    public Date getDataEmissaoMedicao() { return dataEmissaoMedicao; }
    public void setDataEmissaoMedicao(Date dataEmissaoMedicao) { this.dataEmissaoMedicao = dataEmissaoMedicao; }
}
