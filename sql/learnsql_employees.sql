

SELECT
  e1.first_name AS employee_first_name, 
  e1.last_name AS employee_last_name, 
  e1.salary employee_salary, 
  e2.first_name AS manager_first_name, 
  e2.last_name AS manager_last_name
FROM employee e1
LEFT JOIN employee e2
ON e2.id = e1.manager_id;



-- Select the first name, last name, salary and salary grade of employees whose salary fits 
-- between the lower_limit and upper_limit from the salgrade table.

SELECT e.first_name, e.last_name, e.salary, s.grade FROM employee e
LEFT JOIN salgrade s
ON (e.salary BETWEEN s.lower_limit AND s.upper_limit);



-- More one-to-many's based on greater than/equal to logic.
SELECT e.first_name, e.last_name, b.benefit_name FROM employee e
INNER JOIN benefits b
ON e.salary >= b.salary_req
WHERE e.id = 5;


-- For each benefit find the number of employees that receive them. 
-- Show two columns: the benefit_name and the count (name that column employee_count). 
-- Don't forget about benefits that aren't owned by anyone.

SELECT b.benefit_name, COUNT(e.id) AS employee_count FROM benefits b
LEFT JOIN employee e
ON e.salary >= b.salary_req
GROUP BY b.benefit_name