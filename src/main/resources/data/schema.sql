
create type gender_enum as enum ('MALE', 'FEMALE');

create type member_occupation_enum as enum ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

create table if not exists members (
     id uuid primary key default pg_catalog.gen_random_uuid(),
     first_name varchar(50) not null,
     last_name varchar(50) not null,
     birth_date date not null ,
     gender gender_enum not null ,
     address varchar(255)not null,
     profession varchar(255)not null,
     phone_number varchar(10)not null,
     email varchar(255)not null,
     occupation member_occupation_enum not null,
     created_at timestamp without time zone default now()
);

create table if not exists collectivities (
    id uuid primary key default pg_catalog.gen_random_uuid(),
    location varchar(255) not null,
    president_id uuid references members(id),
    vice_president_id uuid references  members(id),
    treasurer_id uuid references members(id),
    secretary_id uuid references members(id)
);

create table if not exists member_collectivity (
    member_id uuid not null references members(id),
    collectivity_id uuid not null references collectivities(id)
);

create table if not exists member_referees (
    member_id uuid references members(id) on delete cascade,
    referee_id uuid references members(id) on delete cascade,
    primary key (member_id, referee_id)
);

ALTER TABLE collectivities
    ADD COLUMN number VARCHAR UNIQUE,
    ADD COLUMN name VARCHAR UNIQUE;

create type frequency_enum as enum ('WEEKLY', 'MONTHLY', 'ANNUALY', 'PUNCTUALLY');

create type status_enum as enum ('ACTIVE', 'INACTIVE');

create table if not exists membership_fee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    collectivity_id UUID NOT NULL,
    eligible_from DATE NOT NULL,
    frequency frequency_enum NOT NULL,
    amount numeric(10,2) NOT NULL,
    label VARCHAR,
    status status_enum NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_collectivity_fee
        FOREIGN KEY (collectivity_id)
            REFERENCES collectivities(id) ON DELETE CASCADE
);

create type account_type_enum as enum ('CASH', 'MOBILE_BANKING', 'BANK_TRANSFER');

create table if not exists member_payment (
    id uuid primary key default gen_random_uuid(),
    member_id uuid not null
        references members(id) on delete cascade,
    membership_fee_id uuid not null
        references membership_fee(id) on delete cascade,
    amount numeric(10,2) not null,
    payment_mode account_type_enum not null,
    account_credited_id uuid,
    creation_date timestamp default now()
);

create table if not exists collectivity_transaction (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    collectivity_id uuid not null
        references collectivities(id) on delete cascade,
    member_id uuid not null
        references members(id) on delete cascade,
    amount numeric(10,2) not null,
    payment_mode account_type_enum not null,
    account_credited_id uuid,
    creation_date timestamp default now()
);

create table if not exists account (
    id uuid primary key default gen_random_uuid(),
    collectivity_id uuid references collectivities(id),
    member_id uuid references members(id),
    amount numeric(10,2) not null default 0,
    type account_type_enum not null
);

create type mobil_service_enum as enum ('AIRTEL_MONEY' , 'MVOLA', 'ORANGE_MONEY');

create table if not exists mobil_account (
    id uuid primary key references account(id) on delete cascade,
    holder_name varchar(255) not null,
    mobil_bank_service mobil_service_enum not null,
    number varchar(10) not null
);

create type bank_name_enum as enum ('BRED', 'MCB', 'BMOI', 'BOA', 'BGFI', 'AFG', 'ACCES_BAQUE', 'BAOBAB', 'SIPEM');

create table if not exists bank_account (
    id uuid primary key references account(id) on delete cascade,
    holder_name varchar(255) not null,
    bank_name bank_name_enum not null,
    bank_code varchar(5) not null,
    bank_branchCode varchar(5) not null,
    bank_account_number varchar(11) not null,
    bank_account_key varchar(2) not null
)