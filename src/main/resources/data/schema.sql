
CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_type AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');
CREATE TYPE account_type_enum AS ENUM ('CASH', 'BANK', 'MOBILE_MONEY');
CREATE TYPE membership_frequency AS ENUM ('WEEKLY', 'MONTHLY', 'ANNUALLY', 'PUNCTUALLY');
CREATE TYPE payment_method_enum AS ENUM ('CASH', 'MOBILE_MONEY', 'BANK_TRANSFER');
CREATE TYPE attendance_status_enum AS ENUM ('MISSING', 'ATTENDED', 'UNDEFINED');
CREATE TYPE bank_name_enum AS ENUM ( 'BRED', 'MCB', 'BMOI', 'BOA', 'BGFI', 'AFG', 'ACCES_BAQUE', 'BAOBAB', 'SIPEM' );
CREATE TYPE mobile_banking_service_enum as ENUM ('AIRTEL_MONEY', 'MVOLA' ,'ORANGE_MONEY');

CREATE TABLE collectivities (
    id VARCHAR(50) PRIMARY KEY,
    number INTEGER UNIQUE NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    location VARCHAR(100) NOT NULL,
    agricultural_specialty VARCHAR(100) NOT NULL
);

CREATE TABLE members (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) NOT NULL REFERENCES collectivities(id),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender gender_type NOT NULL,
    address TEXT NOT NULL,
    profession VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    membership_date DATE NOT NULL,
    occupation occupation_type NOT NULL,
    registration_fee_paid BOOLEAN DEFAULT FALSE,
    membership_dues_paid BOOLEAN DEFAULT FALSE
);

CREATE TABLE member_referees (
    member_id VARCHAR(50) REFERENCES members(id),
    referee_id VARCHAR(50) REFERENCES members(id),
    relationship VARCHAR(50),
    PRIMARY KEY (member_id, referee_id)
);

CREATE TABLE accounts (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) NOT NULL REFERENCES collectivities(id),
    account_type account_type_enum NOT NULL,
    holder_name VARCHAR(100),
    bank_name bank_name_enum,
    bank_code INTEGER,
    bank_branch_code INTEGER,
    bank_account_number INTEGER,
    bank_account_key INTEGER,
    mobile_banking_service mobile_banking_service_enum,
    mobile_number VARCHAR(20),
    balance DECIMAL(15, 2) DEFAULT 0.00,
    currency VARCHAR(3) DEFAULT 'MGA'
);

CREATE TABLE membership_fees (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) NOT NULL REFERENCES collectivities(id),
    label VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    frequency membership_frequency NOT NULL,
    eligible_since DATE NOT NULL,
    amount DECIMAL(15, 2) NOT NULL
);

CREATE TABLE payments (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) NOT NULL REFERENCES collectivities(id),
    member_id VARCHAR(50) NOT NULL REFERENCES members(id),
    account_id VARCHAR(50) NOT NULL REFERENCES accounts(id),
    amount DECIMAL(15, 2) NOT NULL,
    payment_method payment_method_enum NOT NULL,
    payment_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE transactions (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) NOT NULL REFERENCES collectivities(id),
    debited_member_id VARCHAR(50) REFERENCES members(id),
    credited_account_id VARCHAR(50) NOT NULL REFERENCES accounts(id),
    amount DECIMAL(15, 2) NOT NULL,
    payment_method payment_method_enum NOT NULL,
    created_at DATE DEFAULT CURRENT_DATE
);

CREATE TABLE activities (
    id VARCHAR(50) PRIMARY KEY,
    collectivity_id VARCHAR(50) REFERENCES collectivities(id),
    activity_type VARCHAR(50) NOT NULL,
    activity_date DATE NOT NULL,
    is_mandatory BOOLEAN DEFAULT FALSE
);

CREATE TABLE attendance (
    id VARCHAR(50) PRIMARY KEY,
    member_id VARCHAR(50) NOT NULL REFERENCES members(id),
    activity_id VARCHAR(50) NOT NULL REFERENCES activities(id),
    is_excused BOOLEAN DEFAULT FALSE,
    status attendance_status_enum NOT NULL DEFAULT 'UNDEFINED',
    absence_reason TEXT
);