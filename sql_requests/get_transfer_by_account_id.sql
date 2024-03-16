SELECT tf.*, a.* FROM "transfer" AS tf
INNER JOIN "account" AS a
ON a.id like tf.sender_account_id
GROUP BY tf.id, a.id
HAVING tf.sender_account_id like ?;
