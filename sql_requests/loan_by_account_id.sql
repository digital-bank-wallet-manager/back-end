SELECT bl.*, le.*, a.* from "bank_loan" AS bl
INNER JOIN "loan_evolution" AS le
ON le.id like bl.loan_evolution_id
INNER JOIN "account" AS a
ON a.id like bl.account_id
GROUP BY bl.id, le.id, a.id
HAVING bl.account_id like ?;