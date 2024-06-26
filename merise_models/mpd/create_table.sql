CREATE DATABASE bank_wallet;

\c bank_wallet

CREATE TABLE IF NOT EXISTS"account"(
    id varchar(50) primary key,
    first_name varchar(100),
    last_name varchar(100) NOT NULL,
    birthdate date CHECK((date_part('year', CURRENT_DATE) - date_part('year', birthdate)) >= 21),
    loan_authorization boolean default false,
    monthly_pay double precision, 
    account_ref varchar(50)

);

CREATE TABLE IF NOT EXISTS "category"(
    id serial primary key,
    name varchar(100),
    type varchar (50) check (type ilike 'credit' or type ilike 'debit')
    );

CREATE TABLE IF NOT EXISTS "sub_category"(
    id serial primary key,
    name varchar(100),
    category_id int REFERENCES "category"(id)
    );

CREATE TABLE IF NOT EXISTS "foreign_transfer"(
    id varchar(6) primary key,
    account_ref varchar(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS "transfer"(
    id varchar(50) primary key,
    amount double precision,
    reason text,
    date_time timestamp,
    effective_date timestamp,
    transfer_ref varchar(50),
    receiver_account_id varchar(50) references "account"(id),
    sender_account_id varchar(50) references "account"(id),
    id_foreign_transfer varchar(6) references "foreign_transfer"(id)
    );

CREATE TABLE IF NOT EXISTS "bank_loan"(
    id varchar(50) primary key,
    amount double precision,
    loan_date date,
    interest_above_seven_day double precision,
    account_id varchar(50) references "account"(id),
    interest_seven_day double precision,
    status varchar
    );

CREATE TABLE IF NOT EXISTS "loan_evolution"(
    id varchar(50) primary key,
    date_time timestamp,
    total_interest double precision,
    rest double precision,
    bank_loan_id varchar (50) references "bank_loan"(id)
    );

CREATE TABLE IF NOT EXISTS "provisioning"(
    id varchar(50) primary key,
    amount double precision,
    reason text,
    effective_date date,
    record_date date,
    account_id varchar(50) references "account"(id)
    );

CREATE TABLE IF NOT EXISTS "expense"(
    id varchar(50) primary key,
    amount double precision,
    date_time timestamp,
    account_id varchar (100) references "account"(id)
    );

CREATE TABLE IF NOT EXISTS "transaction"(
    id varchar(50) primary key,
    amount double precision,
    type varchar(50) CHECK (type ilike 'debit' OR type ilike 'credit'),
    date_time timestamp,
    account_id varchar(50) references "account"(id),
    provisioning_id varchar(50) references "provisioning"(id),
    bank_loan_id varchar(50) references "bank_loan"(id),
    transfer_id varchar(50) references "transfer"(id),
    expense_id varchar(50) references "expense"(id),
    sub_category_id int references "sub_category"(id),
    status varchar(50) CHECK (status = 'apending' OR status ilike 'canceled' OR status ilike 'done')
    );

CREATE TABLE IF NOT EXISTS "balance"(
    id varchar(50) primary key,
    amount double precision,
    date_time timestamp,
    account_id varchar(50) references "account"(id),
    transaction_id varchar(50) references "transaction"(id)
);