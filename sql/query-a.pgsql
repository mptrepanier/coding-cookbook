

-- A. Select the names of all salespeople that that have an order with samsonic.\
-- COMMON TABLE EXPRESSIONS (CTE) METHOD
-- Define first.
WITH customer_id (id)
AS
(
    SELECT id FROM customers WHERE customer = 'Samsonic'
),
-- Separate with comma.
s (salesperson_id)
AS 
(
SELECT distinct(salesperson_id) FROM orders o
INNER JOIN customer_id ON o.cust_id = customer_id.id
)
-- Define final.
SELECT first_name FROM salespeople
INNER JOIN s ON s.salesperson_id = id;
