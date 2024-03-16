SELECT pv.*, a.* FROM "provisioning" AS pv
INNER JOIN "account" AS a
ON a.id like pv.account_id
GROUP BY pv.id, a.id
HAVING pv.account_id like ?;