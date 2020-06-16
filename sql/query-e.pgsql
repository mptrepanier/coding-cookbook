--E. What salespeople have sold more than 1400 total units?
SELECT salesperson_id FROM orders
GROUP BY salesperson_id
HAVING SUM(amount) > 1400;