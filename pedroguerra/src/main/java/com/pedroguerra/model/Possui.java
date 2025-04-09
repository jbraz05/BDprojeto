package com.pedroguerra.model;

public class Possui{
    private String codigoLocalizacaoAtuacao;
    private String empresaCnpj;
    private String servicoId;

    public Possui(String codigoLocalizacaoAtuacao, String empresaCnpj, String servicoId){
        this.codigoLocalizacaoAtuacao = codigoLocalizacaoAtuacao;
        this.empresaCnpj = empresaCnpj;
        this.servicoId = servicoId;
    }

    public String getCodigoLocalizacaoAtuacao(){
        return codigoLocalizacaoAtuacao;
    }

    public void setCodigoLocalizacaoAtuacao(String codigoLocalizacaoAtuacao){
        this.codigoLocalizacaoAtuacao = codigoLocalizacaoAtuacao;
    }

    public String getEmpresaCnpj(){
        return empresaCnpj;
    }

    public void setEmpresaCnpj(String empresaCnpj){
        this.empresaCnpj = empresaCnpj;
    }

    public String getServicoId(){
        return servicoId;
    }

    public void setServicoId(String servicoId){
        this.servicoId = servicoId;
    }
}