ALTER TABLE
   sukeroku.notes_bodyparts DROP CONSTRAINT notes_bodyparts_fk_note_id_fkey;

ALTER TABLE
   sukeroku.notes_medicine DROP CONSTRAINT notes_medicine_fk_note_id_fkey;

ALTER TABLE
   sukeroku.notes_images DROP CONSTRAINT notes_images_fk_images_id_fkey;

ALTER TABLE
   sukeroku.users_bodyparts DROP CONSTRAINT users_bodyparts_fk_user_id_fkey;

ALTER TABLE
   sukeroku.users_medicine DROP CONSTRAINT users_medicine_fk_user_id_fkey;

ALTER TABLE
   sukeroku.users_notes DROP CONSTRAINT users_notes_fk_note_id_fkey;

ALTER TABLE
   sukeroku.users_notes DROP CONSTRAINT users_notes_fk_user_id_fkey;

DROP TABLE sukeroku.notes_bodyparts;

DROP TABLE sukeroku.notes_medicine;

DROP TABLE sukeroku.notes_images;

DROP TABLE sukeroku.users_bodyparts;

DROP TABLE sukeroku.users_medicine;

DROP TABLE sukeroku.users_notes;

ALTER TABLE
   sukeroku.users
ALTER COLUMN
   user_id TYPE char(36) USING user_id :: char;

ALTER TABLE
   sukeroku.pain_records
ADD
   COLUMN user_id char(36);
