
create type gender_enum as enum ('MALE', 'FEMALE');

create type member_occupation_enum as enum ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

create table members (
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

create table collectivities (
    id uuid primary key default pg_catalog.gen_random_uuid(),
    location varchar(255) not null,
    president_id uuid references members(id),
    vice_president_id uuid references  members(id),
    treasurer_id uuid references members(id),
    secretary_id uuid references members(id)
);

create table member_collectivity (
    member_id uuid not null references members(id),
    collectivity_id uuid not null references collectivities(id)
);

create table member_referees (
    member_id uuid references members(id) on delete cascade,
    referee_id uuid references members(id) on delete cascade,
    primary key (member_id, referee_id)
)

ALTER TABLE collectivities
    ADD COLUMN number VARCHAR UNIQUE,
    ADD COLUMN name VARCHAR UNIQUE;