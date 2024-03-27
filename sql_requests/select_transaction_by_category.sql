/*If there's a given period*/
SELECT SUM(t.amount) AS transaction_sum
FROM "transaction" AS t
         INNER JOIN "sub_category" AS sc
                    ON t.sub_category_id = sc.id
         INNER JOIN "category" AS c
                    ON "sc".category_id = "c".id
GROUP BY c.name, t.date_time
HAVING c.name ilike ?
AND t.date_time BETWEEN ? AND ?;

/*If there's no given period, so for this month by default*/
SELECT SUM(t.amount) AS transaction_sum
FROM "transaction" AS t
         INNER JOIN "sub_category" AS sc
                    ON t.sub_category_id = sc.id
         INNER JOIN "category" AS c
                    ON "sc".category_id = "c".id
GROUP BY c.name, t.date_time
HAVING c.name ilike ?
AND date_part('month', t.date_time) = date_part('month', current_date);