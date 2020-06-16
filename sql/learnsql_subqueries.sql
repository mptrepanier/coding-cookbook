


SELECT o.name, COUNT(m.id) AS members_count FROM orchestras o
INNER JOIN members m
ON o.id = m.orchestra_id
GROUP BY o.name;


-- Subquery in where clause!
SELECT o.name FROM orchestras o
WHERE o.rating > 7.5 AND
o.year > 
(
  SELECT year FROM orchestras where name LIKE 'Chamber Orchestra'
);


-- Subquery in where, different table in subquery.
SELECT o.name FROM orchestras o
WHERE o.city_origin IN 
(
	SELECT city FROM concerts 
  	WHERE YEAR = 2013
);


-- Subquery in "having."
-- Aggregate equal to subquery aggregate.
SELECT
  o.name,
  count(m.id) AS members_count
FROM orchestras o
INNER JOIN members m
  on o.id = m.orchestra_id
 GROUP BY o.name
 HAVING count(m.id) = 
 (
  SELECT COUNT(m.id)
  FROM orchestras o
  JOIN members m
    ON o.id = m.orchestra_id
  WHERE o.name = 'Musical Orchestra'	
 );


 -- Average of results returned by subquery.
SELECT AVG(m.count) FROM 
(
  SELECT COUNT(m.id) FROM orchestras o
  INNER JOIN members m
  ON o.id = m.orchestra_id
  GROUP BY o.id
) m;

-- Name/Number of members greater than the average membership of all orchestras.
SELECT o.name, COUNT(m.id) FROM orchestras o
INNER JOIN members m
ON o.id = m.orchestra_id
GROUP BY o.name
HAVING
COUNT(m.id) >
(SELECT AVG(m.count) FROM 
(
  SELECT COUNT(m.id) FROM orchestras o
  INNER JOIN members m
  ON o.id = m.orchestra_id
  GROUP BY o.id
) m);