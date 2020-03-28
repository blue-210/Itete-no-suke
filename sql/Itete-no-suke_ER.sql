
/* Drop Tables */

DROP TABLE IF EXISTS sukeroku.notes_bodyparts;
DROP TABLE IF EXISTS sukeroku.bodyparts;
DROP TABLE IF EXISTS sukeroku.notes_medicine;
DROP TABLE IF EXISTS sukeroku.users_medicine;
DROP TABLE IF EXISTS sukeroku.medicine;
DROP TABLE IF EXISTS sukeroku.users_notes;
DROP TABLE IF EXISTS sukeroku.notes;
DROP TABLE IF EXISTS sukeroku.users;




/* Create Tables */

-- 部位マスタ
CREATE TABLE sukeroku.bodyparts
(
	-- 部位ID
	body_parts_id bigserial NOT NULL,
	-- 部位名
	body_parts_name varchar(20),
	-- 登録日
	created_at timestamp NOT NULL,
	-- 更新日
	updated_at timestamp NOT NULL,
	PRIMARY KEY (body_parts_id)
) WITHOUT OIDS;


-- 薬マスタ
CREATE TABLE sukeroku.medicine
(
	-- 薬ID
	medicine_id bigserial NOT NULL,
	-- medicine_name
	medicine_name varchar(100) NOT NULL,
	-- 薬メモ
	medicine_memo varchar(200),
	-- 登録日時
	created_at timestamp NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	PRIMARY KEY (medicine_id)
) WITHOUT OIDS;


-- 痛み記録マスタ
CREATE TABLE sukeroku.notes
(
	-- 痛み記録ID
	note_id bigserial NOT NULL,
	-- 痛みレベル
	pain_level varchar(1) NOT NULL,
	-- メモ
	memo varchar(250) NOT NULL,
	-- 登録日時
	created_at timestamp NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	CONSTRAINT notes_pkey PRIMARY KEY (note_id)
) WITHOUT OIDS;


-- 痛み記録_部位マスタ関連テーブル
CREATE TABLE sukeroku.notes_bodyparts
(
	-- 痛み記録ID
	fk_note_id bigint NOT NULL,
	-- 部位ID
	fk_bodyparts_id bigint NOT NULL,
	PRIMARY KEY (fk_note_id, fk_bodyparts_id)
) WITHOUT OIDS;


-- 痛み記録_薬マスタ関連テーブル
CREATE TABLE sukeroku.notes_medicine
(
	-- 痛み記録ID
	fk_note_id bigint NOT NULL,
	-- 薬ID
	fk_medicine_id bigint NOT NULL,
	PRIMARY KEY (fk_note_id, fk_medicine_id)
) WITHOUT OIDS;


-- ユーザーマスタ
CREATE TABLE sukeroku.users
(
	-- ユーザid
	user_id bigserial NOT NULL,
	-- パスワード
	password varchar(255) NOT NULL,
	-- ユーザ名
	user_name varchar(50) NOT NULL,
	-- Eメール
	email varchar(254) UNIQUE,
	-- birthday
	birthday date,
	-- age
	age smallint,
	-- ロール
	role varchar(50) NOT NULL,
	-- status
	status boolean NOT NULL,
	-- 登録日時
	created_at timestamp NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
) WITHOUT OIDS;


-- ユーザー_薬マスタ関連テーブル
CREATE TABLE sukeroku.users_medicine
(
	-- ユーザid
	fk_user_id bigint NOT NULL,
	-- 薬ID
	fk_medicine_id bigint NOT NULL,
	PRIMARY KEY (fk_user_id, fk_medicine_id)
) WITHOUT OIDS;


-- ユーザー_痛み記録マスタ関連テーブル
CREATE TABLE sukeroku.users_notes
(
	-- fkユーザid
	fk_user_id bigint NOT NULL,
	-- fk番号te_id
	fk_note_id bigint NOT NULL,
	CONSTRAINT users_notes_pkey PRIMARY KEY (fk_user_id, fk_note_id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE sukeroku.notes_bodyparts
	ADD FOREIGN KEY (fk_bodyparts_id)
	REFERENCES sukeroku.bodyparts (body_parts_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sukeroku.notes_medicine
	ADD FOREIGN KEY (fk_medicine_id)
	REFERENCES sukeroku.medicine (medicine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sukeroku.users_medicine
	ADD FOREIGN KEY (fk_medicine_id)
	REFERENCES sukeroku.medicine (medicine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sukeroku.notes_bodyparts
	ADD FOREIGN KEY (fk_note_id)
	REFERENCES sukeroku.notes (note_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sukeroku.notes_medicine
	ADD FOREIGN KEY (fk_note_id)
	REFERENCES sukeroku.notes (note_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sukeroku.users_notes
	ADD CONSTRAINT users_notes_fk_note_id_fkey FOREIGN KEY (fk_note_id)
	REFERENCES sukeroku.notes (note_id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE sukeroku.users_medicine
	ADD FOREIGN KEY (fk_user_id)
	REFERENCES sukeroku.users (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sukeroku.users_notes
	ADD CONSTRAINT users_notes_fk_user_id_fkey FOREIGN KEY (fk_user_id)
	REFERENCES sukeroku.users (user_id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;



/* Comments */

COMMENT ON TABLE sukeroku.bodyparts IS '部位マスタ';
COMMENT ON COLUMN sukeroku.bodyparts.body_parts_id IS '部位ID';
COMMENT ON COLUMN sukeroku.bodyparts.body_parts_name IS '部位名';
COMMENT ON COLUMN sukeroku.bodyparts.created_at IS '登録日';
COMMENT ON COLUMN sukeroku.bodyparts.updated_at IS '更新日';
COMMENT ON TABLE sukeroku.medicine IS '薬マスタ';
COMMENT ON COLUMN sukeroku.medicine.medicine_id IS '薬ID';
COMMENT ON COLUMN sukeroku.medicine.medicine_name IS 'medicine_name';
COMMENT ON COLUMN sukeroku.medicine.medicine_memo IS '薬メモ';
COMMENT ON COLUMN sukeroku.medicine.created_at IS '登録日時';
COMMENT ON COLUMN sukeroku.medicine.updated_at IS '更新日時';
COMMENT ON TABLE sukeroku.notes IS '痛み記録マスタ';
COMMENT ON COLUMN sukeroku.notes.note_id IS '痛み記録ID';
COMMENT ON COLUMN sukeroku.notes.pain_level IS '痛みレベル';
COMMENT ON COLUMN sukeroku.notes.memo IS 'メモ';
COMMENT ON COLUMN sukeroku.notes.created_at IS '登録日時';
COMMENT ON COLUMN sukeroku.notes.updated_at IS '更新日時';
COMMENT ON TABLE sukeroku.notes_bodyparts IS '痛み記録_部位マスタ関連テーブル';
COMMENT ON COLUMN sukeroku.notes_bodyparts.fk_note_id IS '痛み記録ID';
COMMENT ON COLUMN sukeroku.notes_bodyparts.fk_bodyparts_id IS '部位ID';
COMMENT ON TABLE sukeroku.notes_medicine IS '痛み記録_薬マスタ関連テーブル';
COMMENT ON COLUMN sukeroku.notes_medicine.fk_note_id IS '痛み記録ID';
COMMENT ON COLUMN sukeroku.notes_medicine.fk_medicine_id IS '薬ID';
COMMENT ON TABLE sukeroku.users IS 'ユーザーマスタ';
COMMENT ON COLUMN sukeroku.users.user_id IS 'ユーザid';
COMMENT ON COLUMN sukeroku.users.password IS 'パスワード';
COMMENT ON COLUMN sukeroku.users.user_name IS 'ユーザ名';
COMMENT ON COLUMN sukeroku.users.email IS 'Eメール';
COMMENT ON COLUMN sukeroku.users.birthday IS 'birthday';
COMMENT ON COLUMN sukeroku.users.age IS 'age';
COMMENT ON COLUMN sukeroku.users.role IS 'ロール';
COMMENT ON COLUMN sukeroku.users.status IS 'status';
COMMENT ON COLUMN sukeroku.users.created_at IS '登録日時';
COMMENT ON COLUMN sukeroku.users.updated_at IS '更新日時';
COMMENT ON TABLE sukeroku.users_medicine IS 'ユーザー_薬マスタ関連テーブル';
COMMENT ON COLUMN sukeroku.users_medicine.fk_user_id IS 'ユーザid';
COMMENT ON COLUMN sukeroku.users_medicine.fk_medicine_id IS '薬ID';
COMMENT ON TABLE sukeroku.users_notes IS 'ユーザー_痛み記録マスタ関連テーブル';
COMMENT ON COLUMN sukeroku.users_notes.fk_user_id IS 'fkユーザid';
COMMENT ON COLUMN sukeroku.users_notes.fk_note_id IS 'fk番号te_id';



