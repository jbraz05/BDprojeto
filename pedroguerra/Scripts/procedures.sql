USE pedroguerra;
DROP PROCEDURE IF EXISTS proc_dashboard_interativo;

DELIMITER //
CREATE PROCEDURE proc_dashboard_interativo (
    IN entidade VARCHAR(20),
    IN metrica VARCHAR(20)
)
begin
	#Parte das Consultas Relacionadas à Empresa
    IF entidade = 'empresa' THEN
        CASE
            WHEN metrica = 'valor' THEN
                SELECT e.nome, SUM(s.valor_medicao) AS total
                FROM Empresa e
                JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
                JOIN Servico s ON p.fk_servico_id = s.id
                GROUP BY e.nome
                ORDER BY total DESC;

            WHEN metrica = 'quantidade' THEN
                SELECT e.nome, COUNT(*) AS total
                FROM Empresa e
                JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
                GROUP BY e.nome
                ORDER BY total DESC;

            WHEN metrica = 'area' THEN
                SELECT e.nome, SUM(r.area) AS total
                FROM Empresa e
                JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
                JOIN RelatorioServico r ON p.fk_servico_id = r.fk_servico_id
                GROUP BY e.nome
                ORDER BY total DESC;

            WHEN metrica = 'concluidos' THEN
                SELECT e.nome, COUNT(*) AS total
                FROM Empresa e
                JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
                JOIN Servico s ON p.fk_servico_id = s.id
                WHERE s.feito = TRUE
                GROUP BY e.nome
                ORDER BY total DESC;

            WHEN metrica = 'tempo' THEN
                SELECT e.nome,
                       AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
                FROM Empresa e
                JOIN Possui p ON e.cnpj = p.fk_empresa_cnpj
                JOIN Servico s ON p.fk_servico_id = s.id
                GROUP BY e.nome
                ORDER BY total DESC;
        END CASE;
	
    #Parte das Consultas Relacionadas ao Cliente
            
    ELSEIF entidade = 'cliente' THEN
        CASE
            WHEN metrica = 'valor' THEN
                SELECT c.nome, SUM(s.valor_medicao) AS total
                FROM Cliente c
                JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
                JOIN Servico s ON ct.nota_fiscal = s.id
                GROUP BY c.nome
                ORDER BY total DESC;

            WHEN metrica = 'quantidade' THEN
                SELECT c.nome, COUNT(*) AS total
                FROM Cliente c
                JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
                GROUP BY c.nome
                ORDER BY total DESC;

            WHEN metrica = 'area' THEN
                SELECT c.nome, SUM(r.area) AS total
                FROM Cliente c
                JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
                JOIN RelatorioServico r ON ct.nota_fiscal = r.fk_servico_id
                GROUP BY c.nome
                ORDER BY total DESC;

            WHEN metrica = 'concluidos' THEN
                SELECT c.nome, COUNT(*) AS total
                FROM Cliente c
                JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
                JOIN Servico s ON ct.nota_fiscal = s.id
                WHERE s.feito = TRUE
                GROUP BY c.nome
                ORDER BY total DESC;

            WHEN metrica = 'tempo' THEN
                SELECT c.nome,
                       AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
                FROM Cliente c
                JOIN Contrata ct ON c.cnpj_cpf = ct.fk_cliente_cnpj_cpf
                JOIN Servico s ON ct.nota_fiscal = s.id
                GROUP BY c.nome
                ORDER BY total DESC;
        END CASE;

    #Parte das Consultas Relacionadas ao Funcionario
            
    ELSEIF entidade = 'funcionario' THEN
        CASE
            WHEN metrica = 'valor' THEN
                SELECT f.nome, SUM(s.valor_medicao) AS total
                FROM Funcionario f
                JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
                GROUP BY f.nome
                ORDER BY total DESC;

            WHEN metrica = 'quantidade' THEN
                SELECT f.nome, COUNT(*) AS total
                FROM Funcionario f
                JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
                GROUP BY f.nome
                ORDER BY total DESC;

            WHEN metrica = 'area' THEN
                SELECT f.nome, SUM(r.area) AS total
                FROM Funcionario f
                JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
                JOIN RelatorioServico r ON s.id = r.fk_servico_id
                GROUP BY f.nome
                ORDER BY total DESC;

            WHEN metrica = 'concluidos' THEN
                SELECT f.nome, COUNT(*) AS total
                FROM Funcionario f
                JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
                WHERE s.feito = TRUE
                GROUP BY f.nome
                ORDER BY total DESC;

            WHEN metrica = 'tempo' THEN
                SELECT f.nome,
                       AVG(s.periodo_preparatorio + s.periodo_trabalho_campo + s.periodo_trabalho_escritorio) AS total
                FROM Funcionario f
                JOIN Servico s ON f.matricula = s.fk_funcionario_matricula
                GROUP BY f.nome
                ORDER BY total DESC;
        END CASE;
    END IF;
END;
//
DELIMITER ;
