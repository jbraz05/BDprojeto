-- SQLBook: Code
create database pedroguerra;

use pedroguerra;

CREATE TABLE Endereco (
    cep VARCHAR(20) PRIMARY KEY,
    numero VARCHAR(10),
    cidade VARCHAR(100),
    bairro VARCHAR(100),
    rua VARCHAR(150)
);

CREATE TABLE Empresa (
    cnpj VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(100),
    fk_endereco_cep VARCHAR(20),
    FOREIGN KEY (fk_endereco_cep) REFERENCES Endereco(cep)
);

CREATE TABLE Funcionario (
    matricula VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(100),
    fk_supervisor_matricula VARCHAR(20),
    fk_endereco_cep VARCHAR(20),
    FOREIGN KEY (fk_endereco_cep) REFERENCES Endereco(cep),
    FOREIGN KEY (fk_supervisor_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE Emprega (
    fk_Empresa_cnpj VARCHAR(20),
    fk_Funcionario_matricula VARCHAR(20),
    PRIMARY KEY (fk_Empresa_cnpj, fk_Funcionario_matricula),
    FOREIGN KEY (fk_Empresa_cnpj) REFERENCES Empresa(cnpj),
    FOREIGN KEY (fk_Funcionario_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE Socio (
    fk_Funcionario_matricula VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_Funcionario_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE Engenheiro (
    fk_Funcionario_matricula VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_Funcionario_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE OperadorDrone (
    fk_Funcionario_matricula VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_Funcionario_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE Cliente (
    cnpj_cpf VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    fk_Endereco_cep VARCHAR(20),
    FOREIGN KEY (fk_Endereco_cep) REFERENCES Endereco(cep)
);

CREATE TABLE PessoaFisica (
    fk_Cliente_cnpj_cpf VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_Cliente_cnpj_cpf) REFERENCES Cliente(cnpj_cpf)
);

CREATE TABLE PessoaJuridica (
    fk_Cliente_cnpj_cpf VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_Cliente_cnpj_cpf) REFERENCES Cliente(cnpj_cpf)
);

CREATE TABLE Servico (
    id VARCHAR(20) PRIMARY KEY,
    data DATE NOT NULL,
    tipo VARCHAR(50),
    fk_Funcionario_matricula VARCHAR(20),
    FOREIGN KEY (fk_Funcionario_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE VooPanoramico (
    fk_Servico_id VARCHAR(20) PRIMARY KEY,
    fk_OperadorDrone_matricula VARCHAR(20),
    FOREIGN KEY (fk_Servico_id) REFERENCES Servico(id),
    FOREIGN KEY (fk_OperadorDrone_matricula) REFERENCES OperadorDrone(fk_Funcionario_matricula)
);

CREATE TABLE MapeamentoTradicional (
    fk_Servico_id VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_Servico_id) REFERENCES Servico(id)
);

CREATE TABLE Contrata (
    nota_fiscal VARCHAR(50) PRIMARY KEY,
    fk_Cliente_cnpj_cpf VARCHAR(20),
    FOREIGN KEY (fk_Cliente_cnpj_cpf) REFERENCES Cliente(cnpj_cpf)
);


CREATE TABLE LocalizacaoAtuacao (
    codigo VARCHAR(20) PRIMARY KEY,
    nome_pais VARCHAR(100),
    nome_estado VARCHAR(100),
    regiao VARCHAR(100)
);

CREATE TABLE Atua (
    fk_Empresa_cnpj VARCHAR(20),
    fk_Localizacao_Atuacao_codigo VARCHAR(20),
    PRIMARY KEY (fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo),
    FOREIGN KEY (fk_Empresa_cnpj) REFERENCES Empresa(cnpj),
    FOREIGN KEY (fk_Localizacao_Atuacao_codigo) REFERENCES LocalizacaoAtuacao(codigo)
);

CREATE TABLE Possui (
    fk_Servico_id VARCHAR(20),
    fk_Localizacao_Atuacao_codigo VARCHAR(10),
    fk_Empresa_cnpj VARCHAR(20),
    PRIMARY KEY (fk_Servico_id, fk_Localizacao_Atuacao_codigo, fk_Empresa_cnpj),
    FOREIGN KEY (fk_Servico_id) REFERENCES Servico(id),
    FOREIGN KEY (fk_Localizacao_Atuacao_codigo) REFERENCES LocalizacaoAtuacao(codigo),
    FOREIGN KEY (fk_Empresa_cnpj) REFERENCES Empresa(cnpj)
);

CREATE TABLE RelatorioServico (
    fk_Servico_id VARCHAR(20),
    area FLOAT,
    data_relatorio DATE,
    observacoes VARCHAR(500),
    PRIMARY KEY (fk_Servico_id),
    FOREIGN KEY (fk_Servico_id) REFERENCES Servico(id)
);