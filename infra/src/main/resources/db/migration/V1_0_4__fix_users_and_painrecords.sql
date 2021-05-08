-- ALTER TABLE
--    sukeroku.notes_bodyparts DROP CONSTRAINT notes_bodyparts_fk_note_id_fkey;
-- ALTER TABLE
--    sukeroku.notes_medicine DROP CONSTRAINT notes_medicine_fk_note_id_fkey;
-- ALTER TABLE
--    sukeroku.notes_images DROP CONSTRAINT notes_images_fk_images_id_fkey;
-- ALTER TABLE
--    sukeroku.users_bodyparts DROP CONSTRAINT users_bodyparts_fk_user_id_fkey;
-- ALTER TABLE
--    sukeroku.users_medicine DROP CONSTRAINT users_medicine_fk_user_id_fkey;
-- ALTER TABLE
--    sukeroku.users_notes DROP CONSTRAINT users_notes_fk_note_id_fkey;
-- ALTER TABLE
--    sukeroku.users_notes DROP CONSTRAINT users_notes_fk_user_id_fkey;
-- DROP TABLE sukeroku.notes_bodyparts;
-- DROP TABLE sukeroku.notes_medicine;
-- DROP TABLE sukeroku.notes_images;
-- DROP TABLE sukeroku.users_bodyparts;
-- DROP TABLE sukeroku.users_medicine;
-- DROP TABLE sukeroku.users_notes;
CREATE TABLE IF NOT EXISTS sukeroku.users (
   -- ユーザid
   user_id bigserial NOT NULL,
   -- パスワード
   PASSWORD varchar(255) NOT NULL,
   -- ユーザ名
   user_name varchar(50) NOT NULL,
   -- Eメール
   email varchar(254) DEFAULT '' UNIQUE,
   -- birthday
   birthday date,
   -- age
   age smallint,
   -- ロール
   role varchar(50) NOT NULL,
   -- ユーザーステータス : 使用中ならALIVE, 削除済みならDELETED
   STATUS varchar(7) DEFAULT 'ALIVE' NOT NULL,
   -- 登録日時
   created_at timestamp NOT NULL,
   -- 更新日時
   updated_at timestamp NOT NULL,
   CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

ALTER TABLE
   sukeroku.users
ALTER COLUMN
   user_id TYPE char(36);
