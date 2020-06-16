-- Inner join with self for nickname on each line.

SELECT b1.nickname AS member, b2.nickname AS leader FROM band_members b1
INNER JOIN band_members b2
ON b1.band_leader_id = b2.id;

-- Count per band leader.

SELECT b1.nickname, b1.band_id, id_counts.members_count FROM band_members b1
INNER JOIN (
  SELECT band_id, count(*) AS members_count
  FROM band_members
  GROUP BY band_id
) id_counts
ON b1.band_id = id_counts.band_id
WHERE b1.id = b1.band_leader_id;