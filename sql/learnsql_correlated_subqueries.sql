

-- Correlated subqueries are subqueries which refer to the outer query.
-- Each subquery is processed separately for each row in the INNER where caluse.
-- Correlated subqueries can be found in the FROM, WHERE, and HAVING clauses.
-- As well, they may be in a SELECT clause, but have to return exactly one row.

SELECT o.name FROM orchestras o
WHERE o.country_origin IN 
(
	SELECT c.country FROM concerts c
    WHERE c.year = 2003
    AND o.id = c.orchestra_id
);


-- Correlated subqueries can be used to find the best/max/whatever object in a certain category.

SELECT m1.name, m1.wage, m1.experience FROM members m1
WHERE wage = (
  SELECT max(m2.wage) FROM members m2
  WHERE m1.orchestra_id = m2.orchestra_id
);


-- Show the names of hte *most experienced* members of each orchestra, and the orchestra's name.
SELECT m.name AS member, o.name AS orchestra FROM orchestras o
INNER JOIN members m
ON m.orchestra_id = o.id
WHERE m.experience = (
	SELECT MAX(experience) FROM members m2
    WHERE m2.orchestra_id = o.id
);



-- Name of orchestra members who earn more than the average wage of the violinists.
SELECT m1.name FROM members m1
WHERE m1.wage > 
(
  SELECT AVG(m2.wage) FROM members m2
  WHERE position = 'violin'
  AND m1.orchestra_id = m2.orchestra_id
);

-- Name, rating, city of origin, and total number of concerts it held in Ukraine for each Orchestra
-- that originated in Germany.
SELECT o.name,
  o.rating,
  o.city_origin,
  (
    SELECT COUNT(id) FROM concerts c
    WHERE c.country = 'Ukraine'
    AND c.orchestra_id = o.id
  )
FROM orchestras o
WHERE o.country_origin = 'Germany';