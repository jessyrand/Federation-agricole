INSERT INTO collectivities (id, number, name, location, specialization)
    VALUES ('col-1', 1, 'Mpanorina', 'Ambatondrazaka', 'Riziculture'),
           ('col-2', 2, 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture'),
           ('col-3', 3, 'Tantely mamy', 'Brickaville', 'Apiculture')
on conflict do nothing ;

INSERT INTO members (id, collectivity_id, first_name, last_name, date_of_birth, gender, address, profession, phone, email, membership_date, occupation, registration_fee_paid, membership_dues_paid)
    VALUES ('C1-M1', 'col-1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', '2026-01-01', 'PRESIDENT', TRUE, TRUE),
           ('C1-M2', 'col-1', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', '2026-01-01', 'VICE_PRESIDENT', TRUE, TRUE),
           ('C1-M3', 'col-1', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', '2026-01-01', 'SECRETARY', TRUE, TRUE),
           ('C1-M4', 'col-1', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', '2026-01-01', 'TREASURER', TRUE, TRUE),
           ('C1-M5', 'col-1', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C1-M6', 'col-1', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C1-M7', 'col-1', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C1-M8', 'col-1', 'Prénom membre 6', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE) on conflict do nothing ;

INSERT INTO members (id, collectivity_id, first_name, last_name, date_of_birth, gender, address, profession, phone, email, membership_date, occupation, registration_fee_paid, membership_dues_paid)
    VALUES ('C2-M1', 'col-2', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato.', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C2-M2', 'col-2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato.', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C2-M3', 'col-2', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato.', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C2-M4', 'col-2', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C2-M5', 'col-2', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato.', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', '2026-01-01', 'PRESIDENT', TRUE, TRUE),
           ('C2-M6', 'col-2', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', '2026-01-01', 'VICE_PRESIDENT', TRUE, TRUE),
           ('C2-M7', 'col-2', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato.', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', '2026-01-01', 'SECRETARY', TRUE, TRUE),
           ('C2-M8', 'col-2', 'Prénom membre 6', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato.', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', '2026-01-01', 'TREASURER', TRUE, TRUE) on conflict do nothing ;

INSERT INTO members (id, collectivity_id, first_name, last_name, date_of_birth, gender, address, profession, phone, email, membership_date, occupation, registration_fee_paid, membership_dues_paid)
    VALUES ('C3-M1', 'col-3', 'Prénom membre 9', 'Nom membre 9', '1988-01-02', 'MALE', 'Lot 33 J Antsirabe', 'Apiculteur', '034034567', 'member.9@fed-agri.mg', '2026-01-01', 'PRESIDENT', TRUE, TRUE),
           ('C3-M2', 'col-3', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE', 'Lot 2 J Antsirabe', 'Agriculteur', '0338634567', 'member.10@fed-agri.mg', '2026-01-01', 'VICE_PRESIDENT', TRUE, TRUE),
           ('C3-M3', 'col-3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE', 'Lot 8 KM Antsirabe', 'Collecteur', '0338234567', 'member.11@fed-agri.mg', '2026-01-01', 'SECRETARY', TRUE, TRUE),
           ('C3-M4', 'col-3', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur', '0382334567', 'member.12@fed-agri.mg', '2026-01-01', 'TREASURER', TRUE, TRUE),
           ('C3-M5', 'col-3', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE', 'Lot UV 80 Antsirabe.', 'Apiculteur', '0373365567', 'member.13@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C3-M6', 'col-3', 'Prénom membre 14', 'Nom membre 14', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe.', 'Apiculteur', '0378234567', 'member.14@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C3-M7', 'col-3', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE', 'Lot UV 7 Antsirabe', 'Apiculteur', '0374914567', 'member.15@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE),
           ('C3-M8', 'col-3', 'Prénom membre 16', 'Nom membre 16', '1975-08-02', 'MALE', 'Lot UV 8 Antsirabe', 'Apiculteur', '0370634567', 'member.16@fed-agri.mg', '2026-01-01', 'SENIOR', TRUE, TRUE) on conflict do nothing ;

INSERT INTO member_referees (member_id, referee_id) VALUES
('C1-M3', 'C1-M1'), ('C1-M3', 'C1-M2'),
('C1-M4', 'C1-M1'), ('C1-M4', 'C1-M2'),
('C1-M5', 'C1-M1'), ('C1-M5', 'C1-M2'),
('C1-M6', 'C1-M1'), ('C1-M6', 'C1-M2'),
('C1-M7', 'C1-M1'), ('C1-M7', 'C1-M2'),
('C1-M8', 'C1-M6'), ('C1-M8', 'C1-M7'),
('C2-M3', 'C1-M1'), ('C2-M3', 'C1-M2'),
('C2-M4', 'C1-M1'), ('C2-M4', 'C1-M2'),
('C2-M5', 'C1-M1'), ('C2-M5', 'C1-M2'),
('C2-M6', 'C1-M1'), ('C2-M6', 'C1-M2'),
('C2-M7', 'C1-M1'), ('C2-M7', 'C1-M2'),
('C2-M8', 'C1-M6'), ('C2-M8', 'C1-M7'),
('C3-M1', 'C1-M1'), ('C3-M1', 'C1-M2'),
('C3-M2', 'C1-M1'), ('C3-M2', 'C1-M2'),
('C3-M3', 'C3-M1'), ('C3-M3', 'C3-M2'),
('C3-M4', 'C3-M1'), ('C3-M4', 'C3-M2'),
('C3-M5', 'C3-M1'), ('C3-M5', 'C3-M2'),
('C3-M6', 'C3-M1'), ('C3-M6', 'C3-M2'),
('C3-M7', 'C3-M1'), ('C3-M7', 'C3-M2'),
('C3-M8', 'C3-M1'), ('C3-M8', 'C3-M2');

INSERT INTO membership_fees (id, collectivity_id, label, status, frequency, eligible_since, amount)
    VALUES  ('cot-1', 'col-1','Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 100000),
            ('cot-2', 'col-2','Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 100000),
            ('cot-3','col-3', 'Cotisation annuelle', 'ACTIVE', 'ANNUALLY', '2026-01-01', 50000);

INSERT INTO accounts ( id, collectivity_id, account_type, holder_name, mobile_banking_service, mobile_number, balance, currency )
    VALUES ('C1-A-CASH', 'col-1', 'CASH', NULL, NULL, NULL, 0.00, 'MGA'),
           ('C1-A-MOBILE-1', 'col-1', 'MOBILE_MONEY', 'Mpanorina', 'ORANGE_MONEY', '0370489612', 0.00, 'MGA');

INSERT INTO accounts ( id, collectivity_id, account_type, holder_name, mobile_banking_service, mobile_number, balance, currency )
    VALUES ('C2-A-CASH', 'col-2', 'CASH', NULL, NULL, NULL, 0.00, 'MGA'),
           ('C2-A-MOBILE-1', 'col-2', 'MOBILE_MONEY', 'Dobo voalohany', 'ORANGE_MONEY', '0320489612', 0.00, 'MGA');

INSERT INTO accounts ( id, collectivity_id, account_type, holder_name, mobile_banking_service, mobile_number, balance, currency )
    VALUES ('C3-A-CASH', 'col-3', 'CASH', NULL, NULL, NULL, 0.00, 'MGA');

INSERT INTO payments ( id, collectivity_id, member_id, account_id, amount, payment_method, payment_date )
    VALUES  ('PAY-C1-M1-001', 'col-1', 'C1-M1', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
            ('PAY-C1-M2-001', 'col-1', 'C1-M2', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
            ('PAY-C1-M3-001', 'col-1', 'C1-M3', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
            ('PAY-C1-M4-001', 'col-1', 'C1-M4', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
            ('PAY-C1-M5-001', 'col-1', 'C1-M5', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
            ('PAY-C1-M6-001', 'col-1', 'C1-M6', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
            ('PAY-C1-M7-001', 'col-1', 'C1-M7', 'C1-A-CASH', 60000.00,  'CASH', '2026-01-01'),
            ('PAY-C1-M8-001', 'col-1', 'C1-M8', 'C1-A-CASH', 90000.00,  'CASH', '2026-01-01');

INSERT INTO transactions ( id, collectivity_id, debited_member_id, credited_account_id, amount, payment_method, created_at )
    VALUES ('TXN-C1-M1-001', 'col-1', 'C1-M1', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C1-M2-001', 'col-1', 'C1-M2', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C1-M3-001', 'col-1', 'C1-M3', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C1-M4-001', 'col-1', 'C1-M4', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C1-M5-001', 'col-1', 'C1-M5', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C1-M6-001', 'col-1', 'C1-M6', 'C1-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C1-M7-001', 'col-1', 'C1-M7', 'C1-A-CASH', 60000.00,  'CASH', '2026-01-01'),
           ('TXN-C1-M8-001', 'col-1', 'C1-M8', 'C1-A-CASH', 90000.00,  'CASH', '2026-01-01');

INSERT INTO payments ( id, collectivity_id, member_id, account_id, amount, payment_method, payment_date )
    VALUES ('PAY-C2-M1-001', 'col-2', 'C2-M1', 'C2-A-CASH', 60000.00, 'CASH', '2026-01-01'),
           ('PAY-C2-M2-001', 'col-2', 'C2-M2', 'C2-A-CASH', 90000.00, 'CASH', '2026-01-01'),
           ('PAY-C2-M3-001', 'col-2', 'C2-M3', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('PAY-C2-M4-001', 'col-2', 'C2-M4', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('PAY-C2-M5-001', 'col-2', 'C2-M5', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('PAY-C2-M6-001', 'col-2', 'C2-M6', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('PAY-C2-M7-001', 'col-2', 'C2-M7', 'C2-A-MOBILE-1', 40000.00, 'MOBILE_MONEY', '2026-01-01'),
           ('PAY-C2-M8-001', 'col-2', 'C2-M8', 'C2-A-MOBILE-1', 60000.00, 'MOBILE_MONEY', '2026-01-01');

INSERT INTO transactions ( id, collectivity_id, debited_member_id, credited_account_id, amount, payment_method, created_at )
    VALUES ('TXN-C2-M1-001', 'col-2', 'C2-M1', 'C2-A-CASH', 60000.00, 'CASH', '2026-01-01'),
           ('TXN-C2-M2-001', 'col-2', 'C2-M2', 'C2-A-CASH', 90000.00, 'CASH', '2026-01-01'),
           ('TXN-C2-M3-001', 'col-2', 'C2-M3', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C2-M4-001', 'col-2', 'C2-M4', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C2-M5-001', 'col-2', 'C2-M5', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C2-M6-001', 'col-2', 'C2-M6', 'C2-A-CASH', 100000.00, 'CASH', '2026-01-01'),
           ('TXN-C2-M7-001', 'col-2', 'C2-M7', 'C2-A-MOBILE-1', 40000.00, 'MOBILE_MONEY', '2026-01-01'),
           ('TXN-C2-M8-001', 'col-2', 'C2-M8', 'C2-A-MOBILE-1', 60000.00, 'MOBILE_MONEY', '2026-01-01');