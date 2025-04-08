package com.pedroguerra.dto;

import com.pedroguerra.model.Empresa;
import com.pedroguerra.model.Funcionario;

public class Emprega {
    private Funcionario funcionario;
    private Empresa empresa;

    public Emprega(Empresa empresa, Funcionario funcionario){
        this.empresa = empresa;
        this.funcionario = funcionario;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

}
