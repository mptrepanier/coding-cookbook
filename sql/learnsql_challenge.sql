-- For each piece of art that is hosued either in an American or Italian museum,
-- show both its name and the artist's name.

-- Initial thoughts: No aggs, just joins.

SELECT poa.name AS piece_name, a.name AS artist_name FROM piece_of_art poa
INNER JOIN museum m
ON m.id = poa.museum_id
INNER JOIN artist a
ON a.id = poa.artist_id
WHERE m.country = 'USA' or m.country = 'Italy';

-- Agg - count of num of pieces of art in each museum collection.
-- Simple joins again, no subquery needed. (Max, not max where etc.)

SELECT m.name, COUNT(poa.id) AS piece_count FROM museum m
INNER JOIN piece_of_art poa
ON poa.museum_id = m.id
GROUP BY m.name;



-- Name of each museum. (museums table)
-- Count of each type. (Join to poa, agg).
-- Only pieces in same coutnry as their creators birthplace.
-- Subquery not needed.

SELECT m.name, COUNT(poa.id) AS piece_count FROM museum m
INNER JOIN piece_of_art poa
ON poa.museum_id = m.id
INNER JOIN artist a
ON poa.artist_id = a.id
WHERE a.country_origin = m.country
GROUP BY m.name;


-- Agg by each artist.
-- Find max.
SELECT MAX(A.count_by_artist) AS most_pieces_created FROM
(SELECT COUNT(poa.id) AS count_by_artist FROM piece_of_art poa
INNER JOIN artist a
ON poa.artist_id = a.id
GROUP BY a.id) A;


-- Distincts with group by by.
SELECT m1.name AS loaner, m2.name AS borrower FROM museum_loan ml
INNER JOIN museum m1
ON ml.from_museum_id = m1.id
INNER JOIN museum m2
ON ml.to_museum_id = m2.id
GROUP BY m1.name, m2.name;


-- Distincts with distinct.
SELECT DISTINCT m1.name AS loaner, m2.name AS borrower FROM museum_loan ml
INNER JOIN museum m1
ON ml.from_museum_id = m1.id
INNER JOIN museum m2
ON ml.to_museum_id = m2.id;

-- Count of each piece of art by country, artist.
SELECT m.country, a.name, COUNT(poa.id) FROM piece_of_art poa
INNER JOIN museum m
ON poa.museum_id = m.id
INNER JOIN artist a
ON poa.artist_id = a.id
GROUP BY m.country, a.name;

-- Show orchestra members name, average price of their instruments.
-- Tables involved: members, instruments.
SELECT m.name, AVG(i.value) FROM members m
INNER JOIN instruments i
ON m.id = i.owner_id
GROUP BY m.name;


-- Tables involed: orchestra (name), concerts (country/countries) and count, avg rating per country.
-- Only show where orchestras played more than one in a given country.

SELECT o.name, c.country, COUNT(c.id) AS concerts_no, AVG(c.rating) AS avg_rating FROM orchestras o
INNER JOIN concerts c
ON c.orchestra_id = o.id
GROUP BY o.name, c.country
HAVING count(c.id) > 1;