-- Script de criação do trigger de log para inserção de funcionários
-- Banco de dados: pedroguerra

USE pedroguerra;

-- Criação da tabela de log (caso não exista)
CREATE TABLE IF NOT EXISTS LogFuncionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_funcionario VARCHAR(25),
    data_insercao DATETIME
);

-- Remoção do trigger anterior, se existir
DROP TRIGGER IF EXISTS trg_log_funcionario_insert;

-- Criação do trigger que registra inserções na tabela Funcionario
CREATE TRIGGER trg_log_funcionario_insert
AFTER INSERT ON Funcionario
FOR EACH ROW
BEGIN
    INSERT INTO LogFuncionario (nome_funcionario, data_insercao)
    VALUES (NEW.nome, NOW());
END;

-- Criação da tabela de log de serviços
CREATE TABLE IF NOT EXISTS LogServico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_servico VARCHAR(20),
    tipo_servico VARCHAR(50),
    data_insercao DATETIME
);

-- Remoção do trigger antigo, se existir
DROP TRIGGER IF EXISTS trg_log_servico_insert;

-- Trigger que registra a inserção de novos serviços
CREATE TRIGGER trg_log_servico_insert
AFTER INSERT ON Servico
FOR EACH ROW
BEGIN
    INSERT INTO LogServico (id_servico, tipo_servico, data_insercao)
    VALUES (NEW.id, NEW.tipo, NOW());
END;


-- Criação da tabela de log de clientes
CREATE TABLE IF NOT EXISTS LogCliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente VARCHAR(25),
    cnpj_cpf_cliente VARCHAR(20),
    data_insercao DATETIME
);

-- Remoção do trigger antigo, se existir
DROP TRIGGER IF EXISTS trg_log_cliente_insert;

-- Trigger que registra a inserção de novos clientes
CREATE TRIGGER trg_log_cliente_insert
AFTER INSERT ON Cliente
FOR EACH ROW
BEGIN
    INSERT INTO LogCliente (nome_cliente, cnpj_cpf_cliente, data_insercao)
    VALUES (NEW.nome, NEW.cnpj_cpf, NOW());
END;


 -- Trigger para incrementar o capital social da empresa após a medição de um serviço --

DROP TRIGGER IF EXISTS trg_incrementa_capital_empresa;
DELIMITER //

CREATE TRIGGER trg_incrementa_capital_empresa
AFTER UPDATE ON Servico
FOR EACH ROW
BEGIN
    DECLARE cnpj_empresa VARCHAR(14);

    IF NEW.feito = TRUE AND OLD.feito = FALSE THEN
        SELECT fk_empresa_cnpj
        INTO cnpj_empresa
        FROM Possui
        WHERE fk_servico_id = NEW.id
        LIMIT 1;

        IF cnpj_empresa IS NOT NULL AND NEW.valor_medicao IS NOT NULL THEN
            UPDATE Empresa
            SET capital_social = capital_social + NEW.valor_medicao
            WHERE cnpj = cnpj_empresa;
        END IF;
    END IF;
END;
//

DELIMITER ;