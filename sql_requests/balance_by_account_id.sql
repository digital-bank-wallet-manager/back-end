SELECT a.*, ba.* FROM "balance" AS ba
INNER JOIN "account" AS a
ON a.id like ba.account_id
GROUP BY ba.id, a.id
HAVING ba.account_id like ?;