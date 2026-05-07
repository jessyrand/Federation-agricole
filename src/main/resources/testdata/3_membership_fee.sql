insert into membership_fee (id, label, amount, eligible_from, status, frequency, collectivity_id)
values ('cot-1', 'Costisation annuelle', 200000, '2026/01/01', 'ACTIVE', 'ANNUALLY', 'col-1'),
       ('cot-2', 'Famangiana', 20000, '2026/04/30', 'ACTIVE', 'PUNCTUALLY', 'col-1'),
       ('cot-3', 'Costisation annuelle', 200000, '2026/01/01', 'ACTIVE', 'ANNUALLY', 'col-2'),
       ('cot-4', 'Cotisation 2025', 100000, '2025/01/01', 'INACTIVE', 'ANNUALLY', 'col-2'),
       ('cot-5', 'Costisation mensuelle', 25000, '2026/04/01', 'ACTIVE', 'MONTHLY', 'col-3');