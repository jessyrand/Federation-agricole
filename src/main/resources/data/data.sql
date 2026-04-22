INSERT INTO members (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation)
VALUES
    ('a1b2c3d4-0001-0001-0001-000000000001', 'Jean',      'Rakoto',     '1980-03-15', 'MALE',   '12 Rue Pasteur, Antananarivo',   'Agriculteur',   '0321100001', 'jean.rakoto@mail.mg',      'PRESIDENT'),
    ('a1b2c3d4-0002-0002-0002-000000000002', 'Marie',     'Rabe',       '1985-07-22', 'FEMALE', '45 Av. de l Indépendance, Tana', 'Comptable',     '0321100002', 'marie.rabe@mail.mg',       'VICE_PRESIDENT'),
    ('a1b2c3d4-0003-0003-0003-000000000003', 'Paul',      'Andry',      '1990-11-08', 'MALE',   '7 Rue du Commerce, Fianarantsoa','Enseignant',    '0321100003', 'paul.andry@mail.mg',       'TREASURER'),
    ('a1b2c3d4-0004-0004-0004-000000000004', 'Hanta',     'Rasoa',      '1992-01-30', 'FEMALE', '3 Cité Ampefiloha, Tana',        'Secrétaire',    '0321100004', 'hanta.rasoa@mail.mg',      'SECRETARY'),
    ('a1b2c3d4-0005-0005-0005-000000000005', 'Luc',       'Ramiandrisoa','1988-05-17','MALE',   '22 Rue Rainandriamampandry',     'Vétérinaire',   '0321100005', 'luc.ramiandrisoa@mail.mg', 'SENIOR'),
    ('a1b2c3d4-0006-0006-0006-000000000006', 'Noro',      'Randria',    '1995-09-04', 'FEMALE', '10 Lot. Ankadivato, Tana',       'Agronome',      '0321100006', 'noro.randria@mail.mg',     'SENIOR'),
    ('a1b2c3d4-0007-0007-0007-000000000007', 'Solo',      'Rakotondrabe','1998-12-25','MALE',   '5 Rue Ravoninahitriniarivo',     'Technicien',    '0321100007', 'solo.rakotondrabe@mail.mg','JUNIOR'),
    ('a1b2c3d4-0008-0008-0008-000000000008', 'Vola',      'Rabemanantsoa','2000-06-11','FEMALE','18 Bd. du 26 Juin, Mahajanga',   'Étudiante',     '0321100008', 'vola.rabemanantsoa@mail.mg','JUNIOR'),
    ('a1b2c3d4-0009-0009-0009-000000000009', 'Tojo',      'Ranaivoson', '1983-04-02', 'MALE',   '9 Rue Ratsimilaho, Toamasina',  'Pêcheur',       '0321100009', 'tojo.ranaivoson@mail.mg',  'SENIOR'),
    ('a1b2c3d4-0010-0010-0010-000000000010', 'Fanja',     'Rasolofo',   '1993-08-19', 'FEMALE', '31 Av. Philibert Tsiranana',     'Infirmière',    '0321100010', 'fanja.rasolofo@mail.mg',   'SENIOR');

INSERT INTO collectivities (id, location, president_id, vice_president_id, treasurer_id, secretary_id)
VALUES (
           'b9e8f7a6-cafe-cafe-cafe-000000000001',
           'Antananarivo',
           'a1b2c3d4-0001-0001-0001-000000000001',
           'a1b2c3d4-0002-0002-0002-000000000002',
           'a1b2c3d4-0003-0003-0003-000000000003',
           'a1b2c3d4-0004-0004-0004-000000000004'
       );

INSERT INTO member_collectivity (member_id, collectivity_id)
VALUES
    ('a1b2c3d4-0001-0001-0001-000000000001', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0002-0002-0002-000000000002', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0003-0003-0003-000000000003', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0004-0004-0004-000000000004', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0005-0005-0005-000000000005', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0006-0006-0006-000000000006', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0007-0007-0007-000000000007', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0008-0008-0008-000000000008', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0009-0009-0009-000000000009', 'b9e8f7a6-cafe-cafe-cafe-000000000001'),
    ('a1b2c3d4-0010-0010-0010-000000000010', 'b9e8f7a6-cafe-cafe-cafe-000000000001');