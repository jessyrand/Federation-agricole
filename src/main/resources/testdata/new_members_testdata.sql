-- ============================================================
-- NOUVEAUX MEMBRES — COLLECTIVITÉ 1 (col-1)
-- ============================================================

INSERT INTO "member" (
    id,
    first_name,
    last_name,
    birth_date,
    gender,
    address,
    profession,
    phone_number,
    email,
    occupation,
    registration_fee_paid,
    membership_dues_paid,
    registration_date
) VALUES
      ('C1-N1', 'Ando', 'Rakoto', '2001-06-12', 'MALE', 'Ambatolampy', 'Agriculture', '0341122334', 'c1n1@fed.mg', 'JUNIOR', false, false, '2026-04-01'),
      ('C1-N2', 'Fara', 'Rasoa', '2002-02-18', 'FEMALE', 'Antsirabe', 'Commerce', '0332211445', 'c1n2@fed.mg', 'JUNIOR', false, false, '2026-04-01'),
      ('C1-N3', 'Tojo', 'Ravo', '2000-11-09', 'MALE', 'Ambositra', 'Agriculture', '0323344556', 'c1n3@fed.mg', 'JUNIOR', false, false, '2026-05-01'),
      ('C1-N4', 'Lalao', 'Randria', '2003-03-21', 'FEMALE', 'Toamasina', 'Elevage', '0345566778', 'c1n4@fed.mg', 'JUNIOR', false, false, '2026-06-01');


INSERT INTO "collectivity_member" (id, member_id, collectivity_id)
VALUES
    ('cm-c1-n1', 'C1-N1', 'col-1'),
    ('cm-c1-n2', 'C1-N2', 'col-1'),
    ('cm-c1-n3', 'C1-N3', 'col-1'),
    ('cm-c1-n4', 'C1-N4', 'col-1');

INSERT INTO "member_referee" (id, member_refereed_id, member_referee_id)
VALUES
    ('mr-c1-n1-m1', 'C1-N1', 'C1-M1'),
    ('mr-c1-n1-m2', 'C1-N1', 'C1-M2'),

    ('mr-c1-n2-m1', 'C1-N2', 'C1-M1'),
    ('mr-c1-n2-m2', 'C1-N2', 'C1-M2'),

    ('mr-c1-n3-m1', 'C1-N3', 'C1-M1'),
    ('mr-c1-n3-m2', 'C1-N3', 'C1-M2'),

    ('mr-c1-n4-m1', 'C1-N4', 'C1-M1'),
    ('mr-c1-n4-m2', 'C1-N4', 'C1-M2');

-- ============================================================
-- NOUVEAUX MEMBRES — COLLECTIVITÉ 2 (col-2)
-- ============================================================

insert into member (
    id, first_name, last_name, birth_date, gender,
    address, profession, phone_number, email,
    occupation, registration_fee_paid, membership_dues_paid, registration_date
) values
      ('C2-N1', 'Hery', 'Ravelo', '2001-09-14', 'MALE', 'Fianarantsoa', 'Agriculture', '0339988776', 'c2n1@fed.mg', 'JUNIOR', false, false, '2026-03-01'),
      ('C2-N2', 'Sophie', 'Ranaivo', '2002-07-22', 'FEMALE', 'Antsirabe', 'Commerce', '0321122998', 'c2n2@fed.mg', 'JUNIOR', false, false, '2026-03-01'),
      ('C2-N3', 'Mickael', 'Razafindrabe', '2000-05-30', 'MALE', 'Antananarivo', 'Agriculture', '0346677889', 'c2n3@fed.mg', 'JUNIOR', false, false, '2026-03-01');

insert into collectivity_member (id, member_id, collectivity_id) values
                                                                     ('cm-c2-n1', 'C2-N1', 'col-2'),
                                                                     ('cm-c2-n2', 'C2-N2', 'col-2'),
                                                                     ('cm-c2-n3', 'C2-N3', 'col-2');

insert into member_referee (id, member_refereed_id, member_referee_id) values
                                                                           ('mr-c2n1-1', 'C2-N1', 'C1-M1'),
                                                                           ('mr-c2n1-2', 'C2-N1', 'C1-M2'),

                                                                           ('mr-c2n2-1', 'C2-N2', 'C1-M1'),
                                                                           ('mr-c2n2-2', 'C2-N2', 'C1-M2'),

                                                                           ('mr-c2n3-1', 'C2-N3', 'C1-M1'),
                                                                           ('mr-c2n3-2', 'C2-N3', 'C1-M2');


-- ============================================================
-- NOUVEAUX MEMBRES — COLLECTIVITÉ 3 (col-3)
-- ============================================================

insert into member (
    id, first_name, last_name, birth_date, gender,
    address, profession, phone_number, email,
    occupation, registration_fee_paid, membership_dues_paid, registration_date
) values
      ('C3-N1', 'Kevin', 'Rakoto', '2001-01-12', 'MALE', 'Brickaville', 'Apiculture', '0347788991', 'c3n1@fed.mg', 'JUNIOR', false, false, '2026-01-01'),
      ('C3-N2', 'Miora', 'Rasolofonirina', '2002-08-18', 'FEMALE', 'Toamasina', 'Agriculture', '0336677881', 'c3n2@fed.mg', 'JUNIOR', false, false, '2026-02-01'),
      ('C3-N3', 'Nantenaina', 'Rabe', '2000-10-05', 'MALE', 'Vatomandry', 'Commerce', '0325566771', 'c3n3@fed.mg', 'JUNIOR', false, false, '2026-02-01'),
      ('C3-N4', 'Tiana', 'Rafidison', '2003-04-09', 'FEMALE', 'Brickaville', 'Elevage', '0344433221', 'c3n4@fed.mg', 'JUNIOR', false, false, '2026-03-01'),
      ('C3-N5', 'Arnaud', 'Ranaivoson', '2001-12-25', 'MALE', 'Toamasina', 'Agriculture', '0331122337', 'c3n5@fed.mg', 'JUNIOR', false, false, '2026-03-01'),
      ('C3-N6', 'Sarah', 'Andrianarisoa', '2002-06-30', 'FEMALE', 'Brickaville', 'Commerce', '0329988772', 'c3n6@fed.mg', 'JUNIOR', false, false, '2026-03-01');


insert into collectivity_member (id, member_id, collectivity_id) values
                                                                     ('cm-c3-n1', 'C3-N1', 'col-3'),
                                                                     ('cm-c3-n2', 'C3-N2', 'col-3'),
                                                                     ('cm-c3-n3', 'C3-N3', 'col-3'),
                                                                     ('cm-c3-n4', 'C3-N4', 'col-3'),
                                                                     ('cm-c3-n5', 'C3-N5', 'col-3'),
                                                                     ('cm-c3-n6', 'C3-N6', 'col-3');

insert into member_referee (id, member_refereed_id, member_referee_id) values
                                                                           ('mr-c3n1-1', 'C3-N1', 'C3-M1'),
                                                                           ('mr-c3n1-2', 'C3-N1', 'C3-M2'),

                                                                           ('mr-c3n2-1', 'C3-N2', 'C3-M1'),
                                                                           ('mr-c3n2-2', 'C3-N2', 'C3-M2'),

                                                                           ('mr-c3n3-1', 'C3-N3', 'C3-M1'),
                                                                           ('mr-c3n3-2', 'C3-N3', 'C3-M2'),

                                                                           ('mr-c3n4-1', 'C3-N4', 'C3-M1'),
                                                                           ('mr-c3n4-2', 'C3-N4', 'C3-M2'),

                                                                           ('mr-c3n5-1', 'C3-N5', 'C3-M1'),
                                                                           ('mr-c3n5-2', 'C3-N5', 'C3-M2'),

                                                                           ('mr-c3n6-1', 'C3-N6', 'C3-M1'),
                                                                           ('mr-c3n6-2', 'C3-N6', 'C3-M2');