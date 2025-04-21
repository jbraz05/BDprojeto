use pedroguerra;

INSERT INTO LocalizacaoAtuacao (codigo, nome_pais, nome_estado, regiao) VALUES
('PE', 'Brasil', 'Pernambuco', 'Nordeste'),
('RN', 'Brasil', 'Rio Grande do Norte', 'Nordeste'),
('SP', 'Brasil', 'São Paulo', 'Sudeste'),
('RJ', 'Brasil', 'Rio de Janeiro', 'Sudeste'),
('MG', 'Brasil', 'Minas Gerais', 'Sudeste');

INSERT INTO Endereco (cep, numero, cidade, bairro, rua) VALUES
('50000-000', '100', 'Recife', 'Boa Viagem', 'Av. Boa Viagem'),          -- Empresa 1
('59000-000', '200', 'Natal', 'Ponta Negra', 'Rua Praia'),              -- Empresa 2
('01000-000', '300', 'São Paulo', 'Centro', 'Av. Paulista'),            -- Empresa 3
('40000-000', '400', 'Rio de Janeiro', 'Copacabana', 'Av. Atlântica'),  -- Funcionario 1
('30000-000', '500', 'Belo Horizonte', 'Savassi', 'Av. Contorno'),      -- Funcionario 2
('70000-000', '600', 'Olinda', 'Carmo', 'Rua do Sol');                  -- Funcionario 3


INSERT INTO Empresa (cnpj, nome, fk_endereco_cep) VALUES
('11.111.111/0001-11', 'Drone Nordeste', '50000-000'),
('22.222.222/0001-22', 'SkyVision Brasil', '59000-000'),
('33.333.333/0001-33', 'Voo Alto SP', '01000-000');

-- Relacionamento com Localização
INSERT INTO Atua (fk_Empresa_cnpj, fk_Localizacao_Atuacao_codigo) VALUES
('11.111.111/0001-11', 'PE'),
('22.222.222/0001-22', 'RN'),
('33.333.333/0001-33', 'SP');

-- Funcionários
INSERT INTO Funcionario (matricula, nome, fk_supervisor_matricula, fk_endereco_cep) VALUES
('MAT001', 'Carlos Silva', NULL, '40000-000'), -- sem supervisor
('MAT002', 'Ana Souza', 'MAT001', '30000-000'), -- supervisora: Carlos
('MAT003', 'Paulo Lima', 'MAT001', '50000-000'); -- supervisado por Carlos

-- Contato dos funcionários
INSERT INTO Contato (codigo, telefone, email, fk_funcionario_matricula, fk_empresa_cnpj) VALUES
('CTF_MAT001', '(81)99999-0001', 'carlos@empresa.com', 'MAT001', '11.111.111/0001-11'),
('CTF_MAT002', '(81)99999-0002', 'ana@empresa.com', 'MAT002', '22.222.222/0001-22'),
('CTF_MAT003', '(81)99999-0003', 'paulo@empresa.com', 'MAT003', '33.333.333/0001-33');

-- Relacionamento Emprega
INSERT INTO Emprega (fk_Empresa_cnpj, fk_Funcionario_matricula) VALUES
('11.111.111/0001-11', 'MAT001'),
('22.222.222/0001-22', 'MAT002'),
('33.333.333/0001-33', 'MAT003');