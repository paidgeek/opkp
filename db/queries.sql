SELECT *
FROM fir_food;

SELECT ((SELECT COUNT(*)
        FROM fir_food
        WHERE SCINAM IS NULL OR SCINAM = '')
        /
        (SELECT COUNT(*)
		FROM fir_food)) * 100.0;



SELECT (SELECT COUNT(*)
        FROM fir_food
        WHERE ORIGFDNM IS NULL OR ORIGFDNM = '')
        /
        (SELECT COUNT(*)
		FROM fir_food) * 100.0;



EXPLAIN SELECT ORIGFDNM, VALUEID, ORIGCPNM, SELVAL FROM fir_food
JOIN fir_value USING(ORIGFDCD)
JOIN fir_component ON(fir_value.COMPID = fir_component.ECOMPID)
WHERE ORIGFDCD = '01001';

SELECT * FROM fir_food
WHERE MATCH (ORIGFDNM) AGAINST('Sir Blue');


-- FOOD SEARCH --
ALTER TABLE fir_food ADD FULLTEXT(ORIGFDNM);

DROP PROCEDURE IF EXISTS search_foods;
DELIMITER //
CREATE PROCEDURE search_foods(`keywords` VARCHAR(255), `offset` INT, `count` INT)
BEGIN

DECLARE total INT DEFAULT 0;

CREATE TEMPORARY TABLE results ENGINE = MEMORY
SELECT SQL_CALC_FOUND_ROWS ORIGFDCD, ORIGFDNM, ENGFDNAM, 0 AS `total`,
(MATCH(ORIGFDNM) AGAINST(keywords WITH QUERY EXPANSION) * 0.05) AS m1,
(MATCH(ORIGFDNM) AGAINST(keywords)) AS m2
FROM fir_food
ORDER BY m1 + m2 DESC
LIMIT `offset`, `count`;

SELECT FOUND_ROWS() INTO total;
UPDATE results SET results.total = total LIMIT 1;

SELECT * FROM results;

DROP TEMPORARY TABLE IF EXISTS results;

END
//
DELIMITER ;

CALL search_foods("awdnkadw", 0, 10);
