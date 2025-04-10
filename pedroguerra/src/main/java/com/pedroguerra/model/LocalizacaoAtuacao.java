package com.pedroguerra.model;

public class LocalizacaoAtuacao {

    private String codigo;
    private String nomePais;
    private String nomeEstado;
    private String regiao;

    // Construtor vazio (necessário para o Spring/Thymeleaf)
    public LocalizacaoAtuacao() {
    }

    // Construtor completo
    public LocalizacaoAtuacao(String codigo, String nomePais, String nomeEstado, String regiao) {
        this.codigo = codigo;
        this.nomePais = nomePais;
        this.nomeEstado = nomeEstado;
        this.regiao = regiao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public String getNomePais() {
        return nomePais;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
