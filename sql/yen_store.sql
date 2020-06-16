


-- Orders by client.
SELECT c.first_name, c.last_name, (CASE WHEN o.ORDER_COUNT IS NULL THEN 0 ELSE o.ORDER_COUNT END) AS order_count FROM clients c
LEFT JOIN (
  SELECT client_id, count(*) AS order_count FROM orders
  GROUP BY client_id
) o
ON o.client_id = c.id;

-- For each order id, get first/last, sum of revenue.
SELECT o.id AS order_id, c.first_name, c.last_name, p.revenue FROM orders o
INNER JOIN clients c
ON c.id = o.client_id
INNER JOIN (
  SELECT order_id, sum(price) AS revenue FROM order_items
  GROUP BY order_id
) p
ON p.order_id = o.id


-- Step one. For each client, get total amount by category.
-- Requires info from OI, O, P, CAT
SELECT SUM(oi.price) AS total_amount, cat.name, c.first_name, c.last_name
FROM order_items oi
INNER JOIN orders o
ON o.id = oi.order_id
INNER JOIN products p 
ON oi.product_id = p.id
INNER JOIN categories cat
ON cat.id = p.category_id
INNER JOIN clients c
on c.id = o.client_id
GROUP BY cat.name, c.first_name, c.last_name