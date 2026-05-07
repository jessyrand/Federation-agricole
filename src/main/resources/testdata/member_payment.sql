insert into member_payment (
    id,
    amount,
    creation_date,
    member_debited_id,
    membership_fee_id,
    payment_mode,
    financial_account_id
) values
-- =========================
-- COL-1
-- =========================
('MP-1', 200000.00, '2026-01-01', 'C1-M1', 'cot-1', 'CASH', 'C1-A-CASH'),
('MP-2', 200000.00, '2026-01-01', 'C1-M2', 'cot-1', 'CASH', 'C1-A-CASH'),
('MP-3', 200000.00, '2026-01-01', 'C1-M3', 'cot-1', 'MOBILE_BANKING', 'C1-A-MOBILE-1'),
('MP-4', 200000.00, '2026-01-01', 'C1-M4', 'cot-1', 'MOBILE_BANKING', 'C1-A-MOBILE-1'),
('MP-5', 150000.00, '2026-01-01', 'C1-M5', 'cot-1', 'MOBILE_BANKING', 'C1-A-MOBILE-1'),
('MP-6', 100000.00, '2026-05-01', 'C1-M6', 'cot-1', 'CASH', 'C1-A-CASH'),
('MP-7',  60000.00, '2026-05-01', 'C1-M7', 'cot-1', 'CASH', 'C1-A-CASH'),
('MP-8',  90000.00, '2026-05-01', 'C1-M8', 'cot-1', 'CASH', 'C1-A-CASH'),

-- =========================
-- COL-2
-- =========================
('MP-9',  120000.00, '2026-01-01', 'C1-M1', 'cot-3', 'CASH', 'C2-A-CASH'),
('MP-10', 180000.00, '2026-01-01', 'C1-M2', 'cot-3', 'CASH', 'C2-A-CASH'),
('MP-11', 200000.00, '2026-01-01', 'C1-M3', 'cot-3', 'CASH', 'C2-A-CASH'),
('MP-12', 200000.00, '2026-01-01', 'C1-M4', 'cot-3', 'CASH', 'C2-A-CASH'),
('MP-13', 200000.00, '2026-01-01', 'C1-M5', 'cot-3', 'CASH', 'C2-A-CASH'),
('MP-14', 200000.00, '2026-01-01', 'C1-M6', 'cot-3', 'CASH', 'C2-A-CASH'),
('MP-15',  80000.00, '2026-01-01', 'C1-M7', 'cot-3', 'MOBILE_BANKING', 'C2-A-MOBILE-1'),
('MP-16', 120000.00, '2026-01-01', 'C1-M8', 'cot-3', 'MOBILE_BANKING', 'C2-A-MOBILE-1'),

-- =========================
-- COL-3
-- =========================
('MP-17', 25000.00, '2026-04-01', 'C3-M1', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-1'),
('MP-18', 25000.00, '2026-04-01', 'C3-M2', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-1'),
('MP-19', 25000.00, '2026-04-01', 'C3-M3', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-1'),
('MP-20', 25000.00, '2026-04-01', 'C3-M4', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-1'),
('MP-21', 25000.00, '2026-04-01', 'C3-M5', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-2'),
('MP-22', 25000.00, '2026-04-01', 'C3-M6', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-2'),
('MP-23', 25000.00, '2026-04-01', 'C3-M7', 'cot-5', 'CASH', 'C3-A-CASH'),
('MP-24', 25000.00, '2026-04-01', 'C3-M8', 'cot-5', 'CASH', 'C3-A-CASH'),
('MP-25', 25000.00, '2026-05-01', 'C3-M1', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-1'),
('MP-26', 25000.00, '2026-05-01', 'C3-M2', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-1'),
('MP-27', 15000.00, '2026-05-01', 'C3-M3', 'cot-5', 'MOBILE_BANKING', 'C3-A-MOBILE-1'),
('MP-28', 15000.00, '2026-05-01', 'C3-M4', 'cot-5', 'MOBILE_BANKING', 'C3-A-MOBILE-1'),
('MP-29', 20000.00, '2026-05-01', 'C3-M5', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-2'),
('MP-30', 25000.00, '2026-05-01', 'C3-M6', 'cot-5', 'BANK_TRANSFER', 'C3-A-BANK-2'),
('MP-31',  5000.00, '2026-05-01', 'C3-M7', 'cot-5', 'CASH', 'C3-A-CASH'),
('MP-32',  5000.00, '2026-05-01', 'C3-M8', 'cot-5', 'CASH', 'C3-A-CASH');