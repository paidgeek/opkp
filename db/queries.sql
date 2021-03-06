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


SELECT ORIGFDNM, RECPROC, AUTHOR
FROM fir_food
JOIN fir_recipe USING(ORIGFDCD);


SELECT ORIGFDCD, ORIGFDNM, ENGFDNAM, COMPID, SELVAL, ECOMPID, ORIGCPNM
FROM fir_food
JOIN fir_value USING(ORIGFDCD)
JOIN fir_component ON(ECOMPID = COMPID)
WHERE ORIGFDCD = 'S0201B';

SELECT ORIGFDCD, ORIGFDNM, COUNT(RECID) AS COUNT_RECID
FROM fir_food
JOIN fir_ingredients ON(ORIGFDCD = FOODID)
GROUP BY ORIGFDCD
ORDER BY COUNT_RECID DESC;


SELECT *
FROM fir_food
INNER JOIN fir_value ON((fir_food.ORIGFDCD = fir_value.ORIGFDCD))

SELECT *
FROM (
	SELECT *
    FROM fir_food
    LIMIT 3
) fir_food
INNER JOIN (
	SELECT *
    FROM fir_value
    INNER JOIN fir_value ON fir_value.ORIGFDCD = fir_value.ORIGFDCD
    GROUP BY fir_value.ORIGFDCD
    HAVING COUNT(*) < 2
) fir_value ON(fir_food.ORIGFDCD = fir_value.ORIGFDCD)


SELECT *
FROM fir_food
JOIN fc_foodsubgroup USING(FSGCD);

SELECT *
FROM fir_food
LEFT JOIN fc_foodsubgroup ON((fc_foodsubgroup.FSGCD = fir_food.FSGCD))
LEFT JOIN fc_foodgroup ON((fc_foodgroup.FGCD = fc_foodsubgroup.FGCD))
WHERE (fir_food.ORIGFDCD = '03280')
LIMIT 50


SELECT fir_food.origfdnm      AS 'fir_food.ORIGFDNM', 
       fir_food.origfdcd      AS 'fir_food.ORIGFDCD', 
       fir_value.selval       AS 'fir_value.SELVAL', 
       fir_value.valueid      AS 'fir_value.VALUEID', 
       fir_value.origfdcd     AS 'fir_value.ORIGFDCD', 
       fir_value.compid       AS 'fir_value.COMPID', 
       fir_component.origcpnm AS 'fir_component.ORIGCPNM', 
       fir_component.ecompid  AS 'fir_component.ECOMPID' 
FROM   fir_food 
       LEFT JOIN fir_value 
              ON(( fir_food.origfdcd = fir_value.origfdcd )) 
       LEFT JOIN fir_component 
              ON(( fir_component.ecompid = fir_value.compid )) 
WHERE  ( fir_food.origfdcd = '20008' ) 
LIMIT  0, 100 




