--B. The names of all salespeople that do not have any order with samsonic.
-- Define the criteria for samsonic.
WITH c (id)
AS
(
    SELECT id FROM customers WHERE customer = 'Samsonic'
)

-- Execute with IN.
SELECT 
o.salesperson_id
FROM orders o
GROUP BY o.salesperson_id
HAVING SUM((CASE WHEN o.cust_id IN (SELECT c.id FROM c) THEN 1 ELSE 0 END)) = 0;