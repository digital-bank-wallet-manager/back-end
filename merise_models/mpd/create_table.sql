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
    type varchar(50) CHECK (type ilike 'debit' OR type ilike 'credit'),
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

CREATE TYPE food_and_drinks_cat AS ENUM ('food_and_drinks', 'supply', 'bar_cafe', 'restaurant_fast_food');
CREATE TYPE purchases_and_online_shops_cat AS ENUM ('purchases and online shops', 'pets', 'jewelry and accessories', 'gifts', 'electronics, accessories', 'children', 'house, garden', 'stationary', 'pharmacy, convenience store', 'health and beauty', 'free time', 'clothes and shoes');
CREATE TYPE housing_cat AS ENUM ('housing', 'property insurance', 'energy, public utilities', 'maintenance, repairs', 'rent', 'mortgage loan', 'services');
CREATE TYPE transportation_cat AS ENUM ('transportation', 'long distance', 'taxi', 'public transportation', 'business trip');
CREATE TYPE vehicle_cat AS ENUM ('vehicle', 'vehicle insurance', 'fuel', 'vehicle maintenance', 'rental', 'rentals', 'parking');
CREATE TYPE leisure_cat AS ENUM ('leisure', 'alcohol, tobacco', 'well-being and beauty', 'culture, sporting events', 'education, development', 'life events', 'books, audio, subscriptions', 'lottery, gambling', 'charity, ...', 'healthcare', 'sport, TV fitness, streaming', 'holidays, trips, hotels');
CREATE TYPE multimedia_computer_cat AS ENUM ('multimedia, computer', 'internet', 'software, applications, games', 'postal services', 'mobile phone, phone');
CREATE TYPE financial_expenses_cat AS ENUM ('financial expenses', 'family allowances', 'fines', 'insurances', 'caution', 'charges, fees', 'loans, interests', 'taxes');
CREATE TYPE investments_cat AS ENUM ('investments', 'real estate', 'collections', 'savings', 'financial investments', 'vehicles, personal property');
CREATE TYPE income_cat AS ENUM ('income', 'family allowances', 'gifts', 'cheques, discount coupons', 'contributions grants', 'interests, dividends', 'lottery, gambling', 'loan, rental', 'refunds (taxes, purchases)');
CREATE TYPE others_cat AS ENUM ('others', 'missing');
CREATE TYPE unknown_cat AS ENUM ('unknown expenses', 'unknown entry');

CREATE TABLE IF NOT EXISTS "expense"(
    id varchar(50) primary key,
    amount double precision,
    date_time timestamp,
    pattern varchar(100),
    food_and_drinks food_and_drinks_cat,
    purchases_and_online_shops purchases_and_online_shops_cat,
    housing housing_cat,
    transportation transportation_cat,
    vehicle vehicle_cat,
    leisure leisure_cat,
    multimedia_computer multimedia_computer_cat,
    financial_expenses financial_expenses_cat,
    investments investments_cat,
    income income_cat,
    others others_cat,
    unknown unknown_cat
);