--B. The names of all salespeople that do not have any order with samsonic.
WITH customer_id (id)
AS
(
    SELECT id FROM customers WHERE customer = 'Samsonic'
),
-- Separate with comma.
s1 (salesperson_id)
AS 
(
SELECT distinct(salesperson_id) FROM orders o
INNER JOIN customer_id ON o.cust_id = customer_id.id
),
s2 (salesperson_id)
AS
(
    SELECT distinct(salesperson_id) FROM orders o
    WHERE salesperson_id NOT IN (SELECT s1.salesperson_id FROM s1)
)
-- Define final.
SELECT first_name FROM salespeople
INNER JOIN s2 ON s2.salesperson_id = id;