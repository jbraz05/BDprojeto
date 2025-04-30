INSERT INTO LocalizacaoAtuacao (codigo, nome_pais, nome_estado, regiao) VALUES
('PE', 'Brasil', 'Pernambuco', 'Nordeste'),
('RN', 'Brasil', 'Rio Grande do Norte', 'Nordeste'),
('SP', 'Brasil', 'São Paulo', 'Sudeste'),
('RJ', 'Brasil', 'Rio de Janeiro', 'Sudeste'),
('MG', 'Brasil', 'Minas Gerais', 'Sudeste');

INSERT INTO Endereco (cep, numero, cidade, bairro, rua) VALUES
('50000000', '100', 'Recife', 'Boa Viagem', 'Av. Boa Viagem'),          -- Empresa 1
('59000000', '200', 'Natal', 'Ponta Negra', 'Rua Praia'),              -- Empresa 2
('01000000', '300', 'São Paulo', 'Centro', 'Av. Paulista'),            -- Empresa 3
('40000000', '400', 'Rio de Janeiro', 'Copacabana', 'Av. Atlântica'),  -- Funcionario 1
('30000000', '500', 'Belo Horizonte', 'Savassi', 'Av. Contorno'),      -- Funcionario 2
('70000000', '600', 'Olinda', 'Carmo', 'Rua do Sol');                  -- Funcionario 3


INSERT INTO Empresa (cnpj, nome, fk_endereco_cep, capital_social) VALUES
('11111111000111', 'Drone Nordeste', '50000000', 100000.00),
('22222222000122', 'SkyVision Brasil', '59000000', 150000.00),
('33333333000133', 'Voo Alto SP', '01000000', 200000.00);
-- Relacionamento com Localização
INSERT INTO Atua (fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo) VALUES
('11.111.111/0001-11', 'PE'),
('22.222.222/0001-22', 'RN'),
('33.333.333/0001-33', 'SP');

-- Funcionários
INSERT INTO Funcionario (matricula, nome, salario, fk_supervisor_matricula, fk_endereco_cep) VALUES
('MAT001', 'Carlos Silva', 7000.00, NULL, '40000000'), -- sem supervisor
('MAT002', 'Ana Souza', 6000.00, 'MAT001', '30000000'), -- supervisora: Carlos
('MAT003', 'Paulo Lima', 5500.00, 'MAT001', '50000000'); -- supervisado por Carlos

-- Contato dos funcionários (CNPJs ajustados)
INSERT INTO Contato (codigo, telefone, email, fk_funcionario_matricula, fk_empresa_cnpj) VALUES
('CTF_MAT001', '(81)99999-0001', 'carlos@empresa.com', 'MAT001', '11111111000111'),
('CTF_MAT002', '(81)99999-0002', 'ana@empresa.com', 'MAT002', '22222222000122'),
('CTF_MAT003', '(81)99999-0003', 'paulo@empresa.com', 'MAT003', '33333333000133');

-- Relacionamento Emprega
INSERT INTO Emprega (fk_Empresa_cnpj, fk_Funcionario_matricula) VALUES
('11111111000111', 'MAT001'),
('22222222000122', 'MAT002'),
('33333333000133', 'MAT003');