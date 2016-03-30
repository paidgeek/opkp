DELIMITER //

-- FOOD SEARCH --
ALTER TABLE fir_food ADD FULLTEXT(ORIGFDNM, ENGFDNAM, SCINAM)//
DROP PROCEDURE IF EXISTS search_foods//
CREATE PROCEDURE search_foods(p_keywords TEXT, p_skip INTEGER, p_take INTEGER)
BEGIN

DECLARE total INT DEFAULT 0;

DROP TEMPORARY TABLE IF EXISTS results;
CREATE TEMPORARY TABLE results
SELECT *, 0 AS total,
	(
		MATCH(ORIGFDNM, ENGFDNAM, SCINAM)
		AGAINST(p_keywords)
	)
    AS score
FROM fir_food
HAVING score > 0
ORDER BY score DESC;

SELECT FOUND_ROWS() INTO total;
UPDATE results SET results.total = total LIMIT 1;

SELECT * FROM results
LIMIT p_skip, p_take;

DROP TEMPORARY TABLE IF EXISTS results;

END
//

DELIMITER ;

-- TESTS --
CALL search_foods('*corn* *bread*',0, 100);



