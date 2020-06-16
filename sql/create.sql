CREATE TABLE salespeople (
    id INTEGER PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    salary INTEGER NOT NULL
);
INSERT INTO salespeople (id, first_name, age, salary)
VALUES
    (1, 'Abe', 61, 140000),
    (2, 'Bob', 34, 44000),
    (5, 'Chris', 34, 40000),
    (7, 'Dan', 41, 52000),
    (8, 'Ken', 57, 115000),
    (11, 'Joe', 38, 38000);


CREATE TABLE customers (
    id INTEGER PRIMARY KEY,
    customer VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    industry CHAR(1) NOT NULL
);
INSERT INTO customers (id, customer, city, industry)
VALUES
    (4, 'Samsonic', 'pleasant', 'J'),
    (6, 'Panasung', 'oaktown', 'J'),
    (7, 'Samony', 'jackson', 'B'),
    (9, 'Orange', 'jackson', 'B');

CREATE TABLE orders (
    number INTEGER,
    order_date DATE NOT NULL,
    cust_id INTEGER NOT NULL,
    salesperson_id INTEGER NOT NULL,
    amount INTEGER NOT NULL
)
INSERT INTO orders (number, order_date, cust_id, salesperson_id, amount)
VALUES
    (10, '1996-02-08', 4, 2, 540),
    (20, '1999-01-30', 4, 8, 1800),
    (30, '1995-07-14', 9, 1, 460),
    (40, '1998-01-29', 7, 2, 2400),
    (50, '1998-02-03', 6, 7, 600),
    (60, '1998-03-02', 6, 7, 720),
    (70, '1998-05-06', 9, 7, 150);
