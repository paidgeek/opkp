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



SELECT ORIGFDNM, VALUEID, ORIGCPNM, SELVAL FROM fir_food
INNER JOIN fir_value USING(ORIGFDCD)
INNER JOIN fir_component ON(fir_value.COMPID = fir_component.ECOMPID)
WHERE ORIGFDCD = '01001'
ORDER BY ORIGCPNM;

SELECT *
FROM fir_food
FULL JOIN fir_value USING(ORIGFDCD)
WHERE ORIGFDCD = '01001';


SELECT * FROM fir_food
WHERE MATCH (ORIGFDNM) AGAINST('Sir Blue');


-- FOOD SEARCH --
ALTER TABLE fir_food ADD FULLTEXT(ORIGFDNM, ENGFDNAM, SCINAM);

DROP PROCEDURE IF EXISTS search_foods;
DELIMITER //
CREATE PROCEDURE search_foods(keywords VARCHAR(255), `offset` INT, `count` INT)
BEGIN

DECLARE total INT DEFAULT 0;

CREATE TEMPORARY TABLE results
SELECT SQL_CALC_FOUND_ROWS *, 0 AS total,
	(
		MATCH(ORIGFDNM, ENGFDNAM, SCINAM)
		AGAINST(keywords IN BOOLEAN MODE)
	) / (LENGTH(ORIGFDNM) - LENGTH(REPLACE(ORIGFDNM, ' ', '')) +
		 LENGTH(ENGFDNAM) - LENGTH(REPLACE(ENGFDNAM, ' ', '')) +
		 LENGTH(SCINAM) - LENGTH(REPLACE(SCINAM, ' ', '')))
    AS score
FROM fir_food
ORDER BY score DESC
LIMIT `offset`, `count`;

SELECT FOUND_ROWS() INTO total;
UPDATE results SET results.total = total LIMIT 1;

SELECT * FROM results;

DROP TEMPORARY TABLE IF EXISTS results;

END
//
DELIMITER ;

CALL search_foods('world', 0, 10);

SELECT SQL_CALC_FOUND_ROWS ORIGFDCD,ORIGFDNM,ACTIVE
FROM fir_food
WHERE (`ACTIVE`='T')
LIMIT 0, 100

SELECT FOUND_ROWS() as total;

INSERT INTO fir_food
VALUES('xxxx', 'hello world', 'hello world', '', '', '', '', '', '0100', '37', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '100', '1.00000', '', '0', '', '', '', '', '', '', '', '', '', '', '0003', NULL, NULL, NULL, NULL, NULL, NULL, 'USDA', 'T', NULL, NULL
);

SHOW CREATE TABLE fir_food;

SELECT * FROM fitbit_user;







