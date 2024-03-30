/*If there's a given period*/
SELECT SUM(expense_sum) AS expense_total_sum, category_name FROM (SELECT SUM(e.amount) AS expense_sum, c.name AS category_name
                                                                  FROM "expense" AS e
                                                                           INNER JOIN "transaction" AS t
                                                                                      ON t.expense_id = e.id
                                                                           INNER JOIN "sub_category" AS sc
                                                                                      ON t.sub_category_id = sc.id
                                                                           INNER JOIN "category" AS c
                                                                                      ON sc.category_id = c.id
                                                                  GROUP BY c.name, e.date_time
                                                                  HAVING c.name ilike ?
                                                                  AND e.date_time BETWEEN ? AND ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;

/*by default if there's no period given*/
SELECT SUM(expense_sum) AS expense_total_sum, category_name FROM (SELECT SUM(e.amount) AS expense_sum, c.name AS category_name
                                                                  FROM "expense" AS e
                                                                           INNER JOIN "transaction" AS t
                                                                                      ON t.expense_id = e.id
                                                                           INNER JOIN "sub_category" AS sc
                                                                                      ON t.sub_category_id = sc.id
                                                                           INNER JOIN "category" AS c
                                                                                      ON sc.category_id = c.id
                                                                  GROUP BY c.name, e.date_time
                                                                  HAVING c.name ilike ?) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;
