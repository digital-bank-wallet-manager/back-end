/*If there's a given period*/
SELECT SUM(provisioning_sum) AS total_sum, category_name FROM (SELECT SUM(p.amount) AS provisioning_sum, c.name as category_name
                                                               FROM "provisioning" AS p
                                                                        INNER JOIN "transaction" AS t
                                                                                   ON t.expense_id = p.id
                                                                        INNER JOIN "sub_category" AS sc
                                                                                   ON t.sub_category_id = sc.id
                                                                        INNER JOIN "category" AS c
                                                                                   ON sc.category_id = c.id
                                                               GROUP BY c.name, p.effective_date
                                                               HAVING c.name ilike ?
                                                               AND p.effective_date BETWEEN ? AND ?
                                                              ) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;

/*By default by month*/
SELECT SUM(provisioning_sum) AS total_sum, category_name FROM (SELECT SUM(p.amount) AS provisioning_sum, c.name as category_name
                                                               FROM "provisioning" AS p
                                                                        INNER JOIN "transaction" AS t
                                                                                   ON t.expense_id = p.id
                                                                        INNER JOIN "sub_category" AS sc
                                                                                   ON t.sub_category_id = sc.id
                                                                        INNER JOIN "category" AS c
                                                                                   ON sc.category_id = c.id
                                                               GROUP BY c.name, p.effective_date
                                                               HAVING c.name ilike ?
                                                              ) AS by_sub_category GROUP BY by_sub_category.category_name, category_name;