--F. Rank the salespeople by volume sold.
SELECT salesperson_id FROM orders
GROUP BY salesperson_id
ORDER BY SUM(amount) DESC