-- ============================================================
-- COMPTES CASH  (table cash_account)
-- ============================================================
INSERT INTO "cash_account" (id, collectivity_id)
VALUES ('C1-A-CASH', 'col-1'),
       ('C2-A-CASH', 'col-2'),
       ('C3-A-CASH', 'col-3');

-- ============================================================
-- COMPTES MOBILE MONEY  (table mobile_banking_account)
-- ============================================================
INSERT INTO "mobile_banking_account" (id, holder_name, service, mobile_number, collectivity_id)
VALUES ('C1-A-MOBILE-1', 'Mpanorina', 'ORANGE_MONEY', '0370489612', 'col-1'),
       ('C2-A-MOBILE-1', 'Dobo voalohany', 'ORANGE_MONEY', '0320489612', 'col-2'),
       ('C3-A-MOBILE-1', 'Kolo', 'MVOLA', '0341889612', 'col-3');


-- ============================================================
-- COMPTES BANQUE  (table bank_account)
-- ============================================================

INSERT INTO bank_account (id, holder_name, bank_name, bank_code, branch_code, account_number, "key", collectivity_id) 
VALUES ('C3-A-BANK-1', 'Koto',  'BMOI', 4, 1, '1234567890', 12, 'col-3'),
       ('C3-A-BANK-2', 'Naivo', 'BRED', 8, 3, '4567890123', 58, 'col-3');