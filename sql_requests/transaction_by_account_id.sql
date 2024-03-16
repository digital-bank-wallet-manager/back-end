SELECT ts.*, a.* FROM "transaction" AS ts
INNER JOIN "account" AS a
ON a.id like ts.account_id
GROUP BY ts.id, a.id
HAVING ts.account_id like ?;