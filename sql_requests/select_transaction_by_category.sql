/*If there's a given period*/
SELECT SUM(transaction_sum) AS sum, category_name FROM (SELECT SUM(t.amount) AS transaction_sum, c.name AS category_name
    FROM "transaction" AS t
    LEFT JOIN "sub_category" AS sc
    ON t.sub_category_id = sc.id
    INNER JOIN "category" AS c
    ON "sc".category_id = "c".id
    GROUP BY c.name, t.date_time
    HAVING c.name ilike ?
    AND t.date_time BETWEEN ? AND ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;

/*If there's no given period, so for this month by default*/
SELECT SUM(transaction_sum) AS sum, category_name FROM (SELECT SUM(t.amount) AS transaction_sum, c.name AS category_name
    FROM "transaction" AS t
    LEFT JOIN "sub_category" AS sc
    ON t.sub_category_id = sc.id
    INNER JOIN "category" AS c
    ON "sc".category_id = "c".id
    GROUP BY c.name, t.date_time
    HAVING c.name ilike ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;