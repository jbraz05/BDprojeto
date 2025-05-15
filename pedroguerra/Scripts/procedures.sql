-- ================================================
-- PROCEDURES PARA DASHBOARD INTERATIVO - EMPRESA
-- ================================================

DROP PROCEDURE IF EXISTS proc_valor_total_por_empresa;
DELIMITER //
CREATE PROCEDURE proc_valor_total_por_empresa()
BEGIN
    SELECT e.nome, SUM(s.valor_medicao) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN Servico s ON p.fk_servico_id = s.id
    GROUP BY e.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_quantidade_servicos_por_empresa;
DELIMITER //
CREATE PROCEDURE proc_quantidade_servicos_por_empresa()
BEGIN
    SELECT e.nome, COUNT(*) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    GROUP BY e.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_area_total_por_empresa;
DELIMITER //
CREATE PROCEDURE proc_area_total_por_empresa()
BEGIN
    SELECT e.nome, SUM(r.area) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN RelatorioServico r ON p.fk_servico_id = r.fk_servico_id
    GROUP BY e.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_servicos_concluidos_por_empresa;
DELIMITER //
CREATE PROCEDURE proc_servicos_concluidos_por_empresa()
BEGIN
    SELECT e.nome, COUNT(*) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN Servico s ON p.fk_servico_id = s.id
    WHERE s.feito = TRUE
    GROUP BY e.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_tempo_medio_total_por_empresa;
DELIMITER //
CREATE PROCEDURE proc_tempo_medio_total_por_empresa()
BEGIN
    SELECT e.nome,
           AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
    FROM Empresa e
    JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
    JOIN Servico s ON p.fk_servico_id = s.id
    GROUP BY e.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;


-- ================================================
-- PROCEDURES PARA DASHBOARD INTERATIVO - CLIENTE
-- ================================================

DROP PROCEDURE IF EXISTS proc_valor_total_por_cliente;
DELIMITER //
CREATE PROCEDURE proc_valor_total_por_cliente()
BEGIN
    SELECT c.nome, SUM(s.valor_medicao) AS total
    FROM Cliente c
    JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
    JOIN Servico s ON ct.nota_fiscal = s.id
    GROUP BY c.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_quantidade_servicos_por_cliente;
DELIMITER //
CREATE PROCEDURE proc_quantidade_servicos_por_cliente()
BEGIN
    SELECT c.nome, COUNT(*) AS total
    FROM Cliente c
    JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
    GROUP BY c.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_area_total_por_cliente;
DELIMITER //
CREATE PROCEDURE proc_area_total_por_cliente()
BEGIN
    SELECT c.nome, SUM(r.area) AS total
    FROM Cliente c
    JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
    JOIN RelatorioServico r ON ct.nota_fiscal = r.fk_servico_id
    GROUP BY c.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_servicos_concluidos_por_cliente;
DELIMITER //
CREATE PROCEDURE proc_servicos_concluidos_por_cliente()
BEGIN
    SELECT c.nome, COUNT(*) AS total
    FROM Cliente c
    JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
    JOIN Servico s ON ct.nota_fiscal = s.id
    WHERE s.feito = TRUE
    GROUP BY c.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ------------------------------------------------

DROP PROCEDURE IF EXISTS proc_tempo_medio_total_por_cliente;
DELIMITER //
CREATE PROCEDURE proc_tempo_medio_total_por_cliente()
BEGIN
    SELECT c.nome,
           AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
    FROM Cliente c
    JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
    JOIN Servico s ON ct.nota_fiscal = s.id
    GROUP BY c.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;


-- ===================================================
-- PROCEDURES PARA DASHBOARD INTERATIVO - FUNCIONÁRIO
-- ===================================================

DROP PROCEDURE IF EXISTS proc_valor_total_por_funcionario;
DELIMITER //
CREATE PROCEDURE proc_valor_total_por_funcionario()
BEGIN
    SELECT f.nome, SUM(s.valor_medicao) AS total
    FROM Funcionario f
    JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
    GROUP BY f.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ---------------------------------------------------

DROP PROCEDURE IF EXISTS proc_quantidade_servicos_por_funcionario;
DELIMITER //
CREATE PROCEDURE proc_quantidade_servicos_por_funcionario()
BEGIN
    SELECT f.nome, COUNT(*) AS total
    FROM Funcionario f
    JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
    GROUP BY f.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ---------------------------------------------------

DROP PROCEDURE IF EXISTS proc_area_total_por_funcionario;
DELIMITER //
CREATE PROCEDURE proc_area_total_por_funcionario()
BEGIN
    SELECT f.nome, SUM(r.area) AS total
    FROM Funcionario f
    JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
    JOIN RelatorioServico r ON s.id = r.fk_servico_id
    GROUP BY f.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ---------------------------------------------------

DROP PROCEDURE IF EXISTS proc_servicos_concluidos_por_funcionario;
DELIMITER //
CREATE PROCEDURE proc_servicos_concluidos_por_funcionario()
BEGIN
    SELECT f.nome, COUNT(*) AS total
    FROM Funcionario f
    JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
    WHERE s.feito = TRUE
    GROUP BY f.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;

-- ---------------------------------------------------

DROP PROCEDURE IF EXISTS proc_tempo_medio_total_por_funcionario;
DELIMITER //
CREATE PROCEDURE proc_tempo_medio_total_por_funcionario()
BEGIN
    SELECT f.nome,
           AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
    FROM Funcionario f
    JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
    GROUP BY f.nome
    ORDER BY total DESC;
END;
//
DELIMITER ;
