
create type gender_enum as enum ('MALE', 'FEMALE');

create type member_occupation_enum as enum ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

create table collectivities (
    id uuid primary key default pg_catalog.gen_random_uuid(),
    location varchar(255) not null
);

create table members (
    id uuid primary key default pg_catalog.gen_random_uuid(),
    collectivity_id varchar(255) references collectivities(id),
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    birth_date date,
    gender gender_enum,
    address varchar(255),
    profession varchar(255),
    phone_number varchar(10),
    email varchar(255),
    occupation member_occupation_enum,
    registration_fee_paid boolean default false,
    membership_dues_paid boolean default false,
    created_at timestamp without time zone
);

create table collectivity_structures (
    collectivity_id uuid primary key references collectivities(id) on delete cascade,
    president_id varchar(255) references members(id),
    vice_president_id varchar(255) references  members(id),
    treasurer_id varchar(255) references members(id),
    secretary_id varchar(255) references members(id)
);

create table member_referees (
    member_id varchar(255) references members(id) on delete cascade,
    referee_id varchar(255) references members(id) on delete cascade,
    primary key (member_id, referee_id)
)