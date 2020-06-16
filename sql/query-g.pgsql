--G. Rank each salesperson's orders temporally.
SELECT salesperson_id, number, order_date, row_number() OVER (PARTITION BY salesperson_id ORDER BY order_date) AS order_rank
FROM orders;