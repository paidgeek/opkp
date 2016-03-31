DELIMITER //

-- FOOD SEARCH --
ALTER TABLE fir_food ADD FULLTEXT(ORIGFDNM, ENGFDNAM, SCINAM)//
DROP PROCEDURE IF EXISTS search_foods//
CREATE PROCEDURE search_foods(p_keywords TEXT, p_skip INTEGER, p_take INTEGER)
BEGIN

DECLARE total INT DEFAULT 0;

DROP TEMPORARY TABLE IF EXISTS results;
CREATE TEMPORARY TABLE results
SELECT ORIGFDCD, ORIGFDNM, ENGFDNAM, SCINAM, 0 AS __total,
	(
		MATCH(ORIGFDNM, ENGFDNAM, SCINAM)
		AGAINST(p_keywords IN BOOLEAN MODE)
	)
    AS __score
FROM fir_food
HAVING __score > 0
ORDER BY __score DESC;

SELECT FOUND_ROWS() INTO total;
UPDATE results SET results.__total = total LIMIT 1;

SELECT * FROM results
LIMIT p_skip, p_take;

DROP TEMPORARY TABLE IF EXISTS results;

END
//

-- RECIPE SEARCH
ALTER TABLE fir_recipe ADD FULLTEXT(RECPROC, AUTHOR)//
DROP PROCEDURE IF EXISTS search_recipes//
CREATE PROCEDURE search_recipes(p_keywords TEXT, p_skip INTEGER, p_take INTEGER)
BEGIN

DECLARE total INT DEFAULT 0;

DROP TEMPORARY TABLE IF EXISTS results;
CREATE TEMPORARY TABLE results
SELECT RECID, ORIGFDNM, ENGFDNAM, SCINAM, 0 AS __total,
	(
		MATCH(r.RECPROC, r.AUTHOR)
		AGAINST(p_keywords IN BOOLEAN MODE)
        +
        MATCH(f.ORIGFDNM, f.ENGFDNAM, f.SCINAM)
        AGAINST(p_keywords IN BOOLEAN MODE)
	)
    AS __score
FROM fir_recipe r
JOIN fir_food f USING(ORIGFDCD)
HAVING __score > 0
ORDER BY __score DESC;

SELECT FOUND_ROWS() INTO total;
UPDATE results SET results.__total = total LIMIT 1;

SELECT * FROM results
LIMIT p_skip, p_take;

DROP TEMPORARY TABLE IF EXISTS results;

END
//

DELIMITER ;

-- TESTS --
CALL search_foods('*Dehidrirana* *banana* *ali* *banana* *v* *prahu*',0, 100);
CALL search_recipes('*bread*', 0, 100)





