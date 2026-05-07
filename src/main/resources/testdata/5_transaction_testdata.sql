-- ============================================================
-- TRANSACTIONS — COLLECTIVITÉ 1 (col-1)
-- ============================================================
INSERT INTO "transaction" (id, amount, creation_date, transaction_type, financial_account_id, member_debited_id)
VALUES ('tx-col1-C1M1', 200000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M1'),
       ('tx-col1-C1M2', 200000.00, '2026-01-01', 'IN', 'C1-A-CASH', 'C1-M2'),
       ('tx-col1-C1M3', 200000.00, '2026-01-01', 'IN', 'C1-A-MOBILE-1', 'C1-M3'),
       ('tx-col1-C1M4', 200000.00, '2026-01-01', 'IN', 'C1-A-MOBILE-1', 'C1-M4'),
       ('tx-col1-C1M5', 150000.00, '2026-01-01', 'IN', 'C1-A-MOBILE-1', 'C1-M5'),
       ('tx-col1-C1M6', 100000.00, '2026-05-01', 'IN', 'C1-A-CASH', 'C1-M6'),
       ('tx-col1-C1M7',  60000.00, '2026-05-01', 'IN', 'C1-A-CASH', 'C1-M7'),
       ('tx-col1-C1M8',  90000.00, '2026-05-01', 'IN', 'C1-A-CASH', 'C1-M8');

-- ============================================================
-- TRANSACTIONS — COLLECTIVITÉ 2 (col-2)
-- ============================================================
INSERT INTO "transaction" (id, amount, creation_date, transaction_type, financial_account_id, member_debited_id)
VALUES ('tx-col2-C1M1', 120000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M1'),
       ('tx-col2-C1M2', 180000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M2'),
       ('tx-col2-C1M3', 200000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M3'),
       ('tx-col2-C1M4', 200000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M4'),
       ('tx-col2-C1M5', 200000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M5'),
       ('tx-col2-C1M6', 200000.00, '2026-01-01', 'IN', 'C2-A-CASH', 'C1-M6'),
       ('tx-col2-C1M7',  80000.00, '2026-01-01', 'IN', 'C2-A-MOBILE-1', 'C1-M7'),
       ('tx-col2-C1M8', 120000.00, '2026-01-01', 'IN', 'C2-A-MOBILE-1', 'C1-M8');

-- ============================================================
-- TRANSACTIONS — COLLECTIVITÉ 3 (col-3)
-- ============================================================
INSERT INTO "transaction" (id, amount, creation_date, transaction_type, financial_account_id, member_debited_id)
VALUES
-- AVRIL
('tx-col3-C3M1-A', 25000.00, '2026-04-01', 'IN', 'C3-A-BANK-1', 'C3-M1'),
('tx-col3-C3M2-A', 25000.00, '2026-04-01', 'IN', 'C3-A-BANK-1', 'C3-M2'),
('tx-col3-C3M3-A', 25000.00, '2026-04-01', 'IN', 'C3-A-BANK-1', 'C3-M3'),
('tx-col3-C3M4-A', 25000.00, '2026-04-01', 'IN', 'C3-A-BANK-1', 'C3-M4'),
('tx-col3-C3M5-A', 25000.00, '2026-04-01', 'IN', 'C3-A-BANK-2', 'C3-M5'),
('tx-col3-C3M6-A', 25000.00, '2026-04-01', 'IN', 'C3-A-BANK-2', 'C3-M6'),
('tx-col3-C3M7-A', 25000.00, '2026-04-01', 'IN', 'C3-A-CASH', 'C3-M7'),
('tx-col3-C3M8-A', 25000.00, '2026-04-01', 'IN', 'C3-A-CASH', 'C3-M8'),

-- MAI
('tx-col3-C3M1-B', 25000.00, '2026-05-01', 'IN', 'C3-A-BANK-1', 'C3-M1'),
('tx-col3-C3M2-B', 25000.00, '2026-05-01', 'IN', 'C3-A-BANK-1', 'C3-M2'),
('tx-col3-C3M3-B', 15000.00, '2026-05-01', 'IN', 'C3-A-MOBILE-1', 'C3-M3'),
('tx-col3-C3M4-B', 15000.00, '2026-05-01', 'IN', 'C3-A-MOBILE-1', 'C3-M4'),
('tx-col3-C3M5-B', 20000.00, '2026-05-01', 'IN', 'C3-A-BANK-2', 'C3-M5'),
('tx-col3-C3M6-B', 25000.00, '2026-05-01', 'IN', 'C3-A-BANK-2', 'C3-M6'),
('tx-col3-C3M7-B',  5000.00, '2026-05-01', 'IN', 'C3-A-CASH', 'C3-M7'),
('tx-col3-C3M8-B',  5000.00, '2026-05-01', 'IN', 'C3-A-CASH', 'C3-M8');