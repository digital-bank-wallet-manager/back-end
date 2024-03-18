ALTER TABLE IF EXISTS "balance"
    ADD COLUMN transaction_id varchar(50)
    REFERENCES "transaction"(id);