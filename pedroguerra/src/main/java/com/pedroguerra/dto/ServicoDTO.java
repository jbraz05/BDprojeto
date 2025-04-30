package com.pedroguerra.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ServicoDTO {
    private String id;
    private Date data;
    private String tipo;
    private String fkFuncionarioMatricula;
    private String nomeFuncionario;
    private int periodoPreparatorio;
    private int periodoTrabalhoCampo;
    private int periodoTrabalhoEscritorio;
    private Date prazoTrabalho;
    private BigDecimal valorMedicao;
    private Date dataEmissaoMedicao;
    private boolean feito;


    private BigDecimal area; //Atributos do relatorio
    private Date dataRelatorio;
    private String observacoes;

    private String nomeOperadorDrone; //pode vir null

    public String getNomeOperadorDrone() {
        return nomeOperadorDrone;
    }
    
    public void setNomeOperadorDrone(String nomeOperadorDrone) {
        this.nomeOperadorDrone = nomeOperadorDrone;
    }
    
    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }

    public Date getDataRelatorio() { return dataRelatorio; }
    public void setDataRelatorio(Date dataRelatorio) { this.dataRelatorio = dataRelatorio; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
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

    public boolean isFeito() { return feito; }
    public void setFeito(boolean feito) { this.feito = feito; }

}
