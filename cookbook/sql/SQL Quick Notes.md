# SQL Quick Notes

#### Union vs. Union All

```
-- Union - combines result set of two or more select statements. (ONLY distinct values.)
SELECT city FROM customers
UNION
SELECT city FROM suppliers
ORDER BY city;

-- Union ALL - Combines the result set of two or more select statements.
(ALLOWS duplicates.)
SELECT City FROM Customers
UNION ALL
SELECT City FROM Suppliers
ORDER BY City;
```

### Running Averages

```
SELECT MarketDate,
       ClosingPrice,
       AVG(ClosingPrice) OVER (ORDER BY MarketDate ASC ROWS 9 PRECEDING) AS MA10
FROM   @DailyQuote
```

### Get First Few by Date

```
SELECT 
    sub.end_station_id,
    sub.ranking
FROM
(
    SELECT 
        end_station_id,
        ROW_NUMBER() OVER (PARTITION BY start_station_id ORDER BY started_at ASC) AS ranking
    FROM bikes
) sub
WHERE sub.ranking <= 2;
```

