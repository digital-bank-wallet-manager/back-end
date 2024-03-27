/*If there's a given period*/
SELECT SUM(e.amount) AS expense_sum
FROM "expense" AS e
         INNER JOIN "transaction" AS t
                    ON t.expense_id = e.id
         INNER JOIN "sub_category" AS sc
                    ON t.sub_category_id = sc.id
         INNER JOIN "category" AS c
                    ON sc.category_id = c.id
GROUP BY c.name, e.date_time
HAVING c.name ilike ?
AND e.date_time BETWEEN ? AND ?;

/*by default if there's no period given*/
SELECT SUM(e.amount) AS expense_sum
FROM "expense" AS e
         INNER JOIN "transaction" AS t
                    ON t.expense_id = e.id
         INNER JOIN "sub_category" AS sc
                    ON t.sub_category_id = sc.id
         INNER JOIN "category" AS c
                    ON sc.category_id = c.id
GROUP BY c.name, e.date_time
HAVING c.name ilike ?
AND date_part('month', e.date_time) = date_part('month', current_date);
