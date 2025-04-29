package com.pedroguerra.dto;

public class FuncionarioDTO {
    private String matricula;
    private String nome;
    private String email;
    private String telefone;
    private String fkEnderecoCep;
    private String numeroEndereco;
    private String cidadeEndereco;
    private String bairroEndereco;
    private String ruaEndereco;
    private String fkSupervisorMatricula;
    private boolean socio;
    private boolean engenheiro;
    private boolean operadorDrone;
    private String cnpjEmpresa;
    private float salario;

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getFkEnderecoCep() { return fkEnderecoCep; }
    public void setFkEnderecoCep(String fkEnderecoCep) { this.fkEnderecoCep = fkEnderecoCep; }

    public String getNumeroEndereco() { return numeroEndereco; }
    public void setNumeroEndereco(String numeroEndereco) { this.numeroEndereco = numeroEndereco; }

    public String getCidadeEndereco() { return cidadeEndereco; }
    public void setCidadeEndereco(String cidadeEndereco) { this.cidadeEndereco = cidadeEndereco; }

    public String getBairroEndereco() { return bairroEndereco; }
    public void setBairroEndereco(String bairroEndereco) { this.bairroEndereco = bairroEndereco; }

    public String getRuaEndereco() { return ruaEndereco; }
    public void setRuaEndereco(String ruaEndereco) { this.ruaEndereco = ruaEndereco; }

    public String getFkSupervisorMatricula() { return fkSupervisorMatricula; }
    public void setFkSupervisorMatricula(String fkSupervisorMatricula) { this.fkSupervisorMatricula = fkSupervisorMatricula; }

    public boolean isSocio() { return socio; }
    public void setSocio(boolean socio) { this.socio = socio; }

    public boolean isEngenheiro() { return engenheiro; }
    public void setEngenheiro(boolean engenheiro) { this.engenheiro = engenheiro; }

    public boolean isOperadorDrone() { return operadorDrone; }
    public void setOperadorDrone(boolean operadorDrone) { this.operadorDrone = operadorDrone; }

    public String getCnpjEmpresa() { return cnpjEmpresa; }
    public void setCnpjEmpresa(String cnpjEmpresa) { this.cnpjEmpresa = cnpjEmpresa; }
    
    public float getSalario() { return salario; }
    public void setSalario(float salario) { this.salario = salario; }
}
