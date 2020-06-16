-- We're interested in good games produced between 2000 and 2009. 
-- A good game is a game that got rating higher than 6 and was profitable
-- For each company show company name, its total revenue from good games produced between 2000 and 2009, and the number of good games it produced in this period.
-- Only show companies with good-game revenue over 3 000 000.

SELECT company, count(*), sum(revenue)
FROM games
WHERE rating > 6
AND (revenue > production_cost)
AND production_year BETWEEN 2000 AND 2009
GROUP BY company
HAVING sum(revenue) > 3000000;



