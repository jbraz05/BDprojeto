USE pedroguerra;

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
