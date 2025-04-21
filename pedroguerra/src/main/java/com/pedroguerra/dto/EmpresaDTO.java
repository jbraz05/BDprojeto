package com.pedroguerra.dto;

public class EmpresaDTO {
    private String cnpj;
    private String nome;
    private String email;
    private String telefone;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String nomeEstado;
    private String nomePais;
    private String regiao;

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getNomeEstado() { return nomeEstado; }
    public void setNomeEstado(String nomeEstado) { this.nomeEstado = nomeEstado; }

    public String getNomePais() { return nomePais; }
    public void setNomePais(String nomePais) { this.nomePais = nomePais; }

    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }
}
