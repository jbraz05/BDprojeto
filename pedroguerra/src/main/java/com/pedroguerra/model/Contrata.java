package com.pedroguerra.model;

public class Contrata {

    private String notaFiscal;
    private String cnpjCpf;

    public Contrata(String notaFiscal, String cnpjCpf) {
        this.notaFiscal = notaFiscal;
        this.cnpjCpf = cnpjCpf;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getcnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }
}
