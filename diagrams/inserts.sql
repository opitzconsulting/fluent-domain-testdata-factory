INSERT INTO universitaet(id, name)
VALUES (universitaet_seq.nexval, 'Universität Freiburg');

INSERT INTO fakultaet(id, name, universitaet_id)
VALUES (fakultaet_seq.nextval,
        'Technische Fakultät',
           select id from universitaet where name = 'Universität Freiburg');

INSERT INTO studiengang(id, bezeichnung, fakultaet_id)
VALUES (studiengang_seq.nextval,
        'Informatik',
           select id from fakultaet where name = 'Technische Fakultät');


