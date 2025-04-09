package com.pedroguerra.model;

public class Atua{
    private String codigoLocalizacaoAtuacao;
    private String empresaCnpj;

    public Atua(String codigoLocalizacaoAtuacao, String empresaCnpj){
        this.codigoLocalizacaoAtuacao = codigoLocalizacaoAtuacao;
        this.empresaCnpj = empresaCnpj;
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
}