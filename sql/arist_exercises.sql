-- Artist > 50 years.
-- Birth year after 1800
-- Get names and pieces, rename.

SELECT a.name AS artist_name, p.name AS piece_name FROM artist a
INNER JOIN piece_of_art p
ON a.id = p.artist_id
WHERE (a.death_year - a.birth_year) > 50
AND a.birth_year > 1800;


-- Show names of all pieces of art together with creator names, artist names, and musuem names.
-- Omit unknown artists/nulls.
SELECT a.name AS artist_name, m.name AS museum_name, p.name AS piece_of_art_name
FROM piece_of_art p
INNER JOIN museum m ON p.museum_id = m.id
INNER JOIN artist a ON a.id = p.artist_id;


-- Artist productivity!
SELECT a.name, (a.death_year - a.birth_year) AS years_lived, p.number_of_created
FROM artist a
INNER JOIN (
  SELECT artist_id, count(artist_id) AS number_of_created
  FROM
  piece_of_art
  GROUP BY artist_id
  HAVING
  COUNT(artist_id) >= 1
) p
ON a.id = p.artist_id;