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


SELECT *
FROM fir_food
INNER JOIN fir_value ON((`fir_food`.`ORIGFDCD` = `fir_value`.`ORIGFDCD`))
INNER JOIN fir_component ON((`fir_component`.`ECOMPID` = `fir_value`.`COMPID`));



SELECT COUNT(1) AS __total FROM (SELECT 1
FROM fir_food
INNER JOIN fir_value ON((fir_food.ORIGFDCD = fir_value.ORIGFDCD))
INNER JOIN fir_component ON((fir_component.ECOMPID = fir_value.COMPID))
) AS __total;

SELECT * FROM fc_foodgroup;

SELECT ORIGFDNM, RECPROC, AUTHOR
FROM fir_food
JOIN fir_recipe USING(ORIGFDCD);


SELECT COUNT(*) FROM fir_recipe;



