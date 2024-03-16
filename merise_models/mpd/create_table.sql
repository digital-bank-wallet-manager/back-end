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

CREATE TABLE IF NOT EXISTS "balance"(
    id varchar(50) primary key,
    amount double precision,
    date_time timestamp,
    account_id varchar(50) references "account"(id)
);

CREATE TABLE IF NOT EXISTS "transaction"(
    id varchar(50) primary key, 
    amount double precision,
    type varchar(50),
    date_time timestamp,
    account_id varchar(50) references "account"(id)
);

CREATE TABLE IF NOT EXISTS "transfer"(
    id varchar(50) primary key,
    amount double precision,
    reason text,
    date_time timestamp,
    effective_date timestamp,
    record_date timestamp,
    transfer_ref varchar(50),
    receiver_account_id varchar(50) references "account"(id),
    sender_account_id varchar(50) references "account"(id)
);

CREATE TABLE IF NOT EXISTS "loan_evolution"(
    id varchar(50) primary key,
    date_time timestamp,
    total_interest double precision,
    rest double precision
);

CREATE TABLE IF NOT EXISTS "bank_loan"(
    id varchar(50) primary key,
    amount double precision,
    loan_date date,
    interest double precision,
    account_id varchar(50) references "account"(id),
    loan_evolution_id varchar(50) references "loan_evolution"(id)
);

CREATE TABLE IF NOT EXISTS "provisioning"(
    id varchar(50) primary key,
    amount double precision,
    reason text,
    effective_date date,
    record_date date,
    account_id varchar(50) references "account"(id)
);