--C. Select the names of salespeople that have two or more orders.
SELECT first_name, age FROM salespeople a
INNER JOIN
(
SELECT salesperson_id
FROM orders
GROUP BY salesperson_id
HAVING COUNT(salesperson_id) >= 2
) b
ON a.id = b.salesperson_id;