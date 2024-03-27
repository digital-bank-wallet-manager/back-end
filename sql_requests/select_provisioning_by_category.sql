/*If there's a given period*/
SELECT SUM(p.amount) AS provisioning_sum
FROM "provisioning" AS p
         INNER JOIN "transaction" AS t
                    ON t.expense_id = p.id
         INNER JOIN "sub_category" AS sc
                    ON t.sub_category_id = sc.id
         INNER JOIN "category" AS c
                    ON sc.category_id = c.id
GROUP BY c.name, p.effective_date
HAVING c.name ilike ?
AND p.effective_date BETWEEN ? AND ?;

/*By default by month*/
SELECT SUM(p.amount) AS provisioning_sum
FROM "provisioning" AS p
         INNER JOIN "transaction" AS t
                    ON t.expense_id = p.id
         INNER JOIN "sub_category" AS sc
                    ON t.sub_category_id = sc.id
         INNER JOIN "category" AS c
                    ON sc.category_id = c.id
GROUP BY c.name, p.effective_date
HAVING c.name ilike ?
AND date_part('month', p.effective_date) = date_part('month', current_date);

