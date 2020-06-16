-- 1. Fetch "First Name" using the alias name as WORKER_NAME.
select first_name AS worker_name FROM worker;

-- 2. Fetch first name in uppercase.
select upper(first_name) AS upper_worker_name FROM worker;

-- 3. Unique values of department.
SELECT DISTINCT(department) FROM worker;

-- 4. First three chars of FIRST_NAME.
SELECT substring(first_name from 1 for 3) AS first_three FROM worker;

-- 5. Find position of first 'a' in first name.
SELECT position('a' in  first_name) FROM worker;

-- 6. Trim white space from right side of first name.
SELECT RTRIM(first_name) from worker;

-- 23. Fetch the number of workers in each department by desending order.
SELECT department, count(worker_id) FROM worker
GROUP BY department
ORDER BY count(worker_id) DESC;

-- 24. Print details of workers who are also managers.
SELECT w.* FROM worker w
INNER JOIN
(SELECT worker_ref_id FROM title WHERE lower(worker_title) LIKE 'manager%') t
ON t.worker_ref_id = w.worker_id;

-- 24b. Print details of workers who are also managers.
SELECT w.* FROM worker w
INNER JOIN title t
ON w.worker_id = t.worker_ref_id
AND lower(t.worker_title) LIKE 'manager%';

-- 25. Duplicate records having matching data.
SELECT worker_ref_id, affected_from, count(*) AS count FROM title t
GROUP BY worker_ref_id, affected_from
HAVING count(*) > 1;

-- 26. Write an SQL query to show only odd rows from a table.
SELECT * FROM worker WHERE MOD(worker_id, 2) <> 0;

-- 27. Clone a new table from another table.
-- SELECT * INTO worker_clone FROM worker;

-- 28. Select intersection of two tables.
SELECT * FROM worker
INTERSECT
SELECT * FROM worker_clone;

-- 29. Show records in one table that are not present in another.
SELECT *  FROM worker
EXCEPT
SELECT * FROM worker_clone;

-- 30. Write an SQL query to determine the 3rd highest salary.
SELECT * FROM
(
SELECT salary, dense_rank() OVER (ORDER BY salary desc) AS rank
FROM worker
) ranked
WHERE ranked.rank = 3;

-- 35. Fetch a lsit of employees with the same salary.
SELECT DISTINCT w1.worker_id, w1.first_name, w1.salary
FROM worker w1, worker w2
WHERE w1.salary = w2.salary
AND w1.worker_id != w2.worker_id;

-- 35b. Similar, but with ranking.
SELECT worker_id, first_name, salary, dense_rank() OVER(ORDER BY salary)
FROM worker;

-- 40. SQL Query - departments with less than 5 people.
SELECT department FROM worker
WHERE department IS NOT NULL
GROUP BY department
HAVING count(department) > 2;

-- CUSTOM 1. FIND WORKERS WHO DID NOT RECIEVE A BONUS.
SELECT * FROM worker w
WHERE w.worker_id NOT IN (SELECT distinct(worker_ref_id) FROM bonus);

-- CUSTOM 2. Worker and title.
SELECT CONCAT(CASE WHEN w.first_name IS NOT NULL THEN w.first_name ELSE '' END, ' is ', CASE WHEN t.worker_title IS NOT NULL THEN t.worker_title ELSE '' END) from worker w
INNER JOIN title t
ON t.worker_ref_id = w.worker_id;



