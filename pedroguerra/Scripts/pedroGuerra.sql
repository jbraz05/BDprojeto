CREATE DATABASE pedroguerra;

USE pedroguerra;

CREATE TABLE Endereco (
    cep VARCHAR(8) PRIMARY KEY,
    numero INT,
    cidade VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    rua VARCHAR(150) NOT NULL
);

    CREATE TABLE Empresa (
    cnpj VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(25) NOT NULL,
    capital_social DECIMAL(10,2) NOT NULL,
    fk_endereco_cep VARCHAR(8),
    FOREIGN KEY (fk_endereco_cep) REFERENCES Endereco(cep)
);

CREATE TABLE Funcionario (
    matricula VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(25) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fk_supervisor_matricula VARCHAR(20),
    fk_endereco_cep VARCHAR(8),
    FOREIGN KEY (fk_endereco_cep) REFERENCES Endereco(cep),
    FOREIGN KEY (fk_supervisor_matricula) REFERENCES Funcionario(matricula)
);

CREATE TABLE Contato (
    codigo VARCHAR(20) PRIMARY KEY,
    telefone VARCHAR(15) NOT NULL,
    email VARCHAR(30) NOT NULL,
    fk_funcionario_matricula VARCHAR(20),
    fk_empresa_cnpj VARCHAR(14),
    FOREIGN KEY (fk_funcionario_matricula) REFERENCES Funcionario(matricula),
    FOREIGN KEY (fk_empresa_cnpj) REFERENCES Empresa(cnpj) ON DELETE CASCADE
);

CREATE TABLE Emprega (
    fk_Empresa_cnpj VARCHAR(14),
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
    nome VARCHAR(25) NOT NULL,
    fk_endereco_cep VARCHAR(8) NOT NULL,
    FOREIGN KEY (fk_endereco_cep) REFERENCES Endereco(cep)
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
    tipo VARCHAR(50) NOT NULL,
    fk_funcionario_matricula VARCHAR(20),
    periodo_preparatorio INT,
    periodo_trabalho_campo INT,
    periodo_trabalho_escritorio INT,
    prazo_trabalho DATE,
    valor_medicao DECIMAL(10,2),
    data_emissao_medicao DATE,
    feito BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (fk_funcionario_matricula) REFERENCES Funcionario(matricula)
);



CREATE TABLE VooPanoramico (
    fk_servico_id VARCHAR(20) PRIMARY KEY,
    fk_operadorDrone_matricula VARCHAR(20),
    FOREIGN KEY (fk_servico_id) REFERENCES Servico(id),
    FOREIGN KEY (fk_operadorDrone_matricula) REFERENCES OperadorDrone(fk_funcionario_matricula) ON DELETE SET NULL
);

CREATE TABLE MapeamentoTradicional (
    fk_servico_id VARCHAR(20) PRIMARY KEY,
    FOREIGN KEY (fk_servico_id) REFERENCES Servico(id)
);

CREATE TABLE Contrata (
    nota_fiscal VARCHAR(50) PRIMARY KEY,
    fk_cliente_cnpj_cpf VARCHAR(20),
    FOREIGN KEY (fk_cliente_cnpj_cpf) REFERENCES Cliente(cnpj_cpf)
);

CREATE TABLE LocalizacaoAtuacao (
    codigo VARCHAR(20) PRIMARY KEY,
    nome_pais VARCHAR(100) NOT NULL,
    nome_estado VARCHAR(100) NOT NULL,
    regiao VARCHAR(100) NOT NULL
);

CREATE TABLE Atua (
    fk_empresa_cnpj VARCHAR(14),
    fk_localizacao_atuacao_codigo VARCHAR(20),
    PRIMARY KEY (fk_empresa_cnpj, fk_localizacao_atuacao_codigo),
    FOREIGN KEY (fk_empresa_cnpj) REFERENCES Empresa(cnpj),
    FOREIGN KEY (fk_localizacao_atuacao_codigo) REFERENCES LocalizacaoAtuacao(codigo)
);

CREATE TABLE Possui (
    fk_servico_id VARCHAR(20),
    fk_localizacao_atuacao_codigo VARCHAR(20),
    fk_empresa_cnpj VARCHAR(14),
    PRIMARY KEY (fk_servico_id, fk_localizacao_atuacao_codigo, fk_empresa_cnpj),
    FOREIGN KEY (fk_servico_id) REFERENCES Servico(id),
    FOREIGN KEY (fk_localizacao_atuacao_codigo) REFERENCES LocalizacaoAtuacao(codigo),
    FOREIGN KEY (fk_empresa_cnpj) REFERENCES Empresa(cnpj)
);

CREATE TABLE RelatorioServico (
    fk_servico_id VARCHAR(20),
    area FLOAT NOT NULL,
    data_relatorio DATE NOT NULL,
    observacoes VARCHAR(500) NOT NULL,
    PRIMARY KEY (fk_servico_id),
    FOREIGN KEY (fk_servico_id) REFERENCES Servico(id)
);