DELETE FROM notes_medicine;

DELETE FROM users_medicine;

DELETE FROM medicine;
INSERT INTO medicine (medicine_name, updated_at, created_at) VALUES('お薬1', now(), now());
INSERT INTO medicine (medicine_name, updated_at, created_at) VALUES('お薬2', now(), now());
INSERT INTO medicine (medicine_name, updated_at, created_at) VALUES('お薬3', now(), now());

INSERT INTO users_medicine SELECT '1', medicine_id FROM medicine;
