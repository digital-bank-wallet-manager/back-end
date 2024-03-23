ALTER TABLE IF EXISTS "transaction"
    ADD COLUMN provisioning_id varchar(50)
    REFERENCES "provisioning"(id),
    ADD COLUMN bank_loan_id varchar(50)
    REFERENCES "bank_loan"(id),
    ADD COLUMN transfer_id varchar(50)
    REFERENCES "transfer"(id),
    ADD COLUMN status varchar (50);

ALTER TABLE IF EXISTS "transaction"
    ADD COLUMN expense_id varchar(50)
    REFERENCES "expense"(id);

