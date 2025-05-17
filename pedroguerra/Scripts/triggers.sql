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
