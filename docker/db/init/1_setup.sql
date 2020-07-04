/* Create Tables */

-- 新規テーブル
CREATE TABLE images
(
	-- 部位画像ID
	images_id bigserial NOT NULL,
	-- 部位画像パス
	images_path varchar(200) DEFAULT 'images/default/body_parts_default.jpeg' NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	-- 登録日時
	created_at timestamp NOT NULL,
	PRIMARY KEY (images_id)
) WITHOUT OIDS;


-- 新規テーブル
CREATE TABLE notes_images
(
	-- 痛み記録ID
	fk_note_id bigint NOT NULL,
	-- 部位画像ID
	fk_images_id bigint NOT NULL,
	-- 画像連番
	images_seq int NOT NULL,
	PRIMARY KEY (fk_note_id, fk_images_id, images_seq)
) WITHOUT OIDS;


-- 新規テーブル
CREATE TABLE users_bodyparts
(
	-- ユーザid
	fk_user_id bigint NOT NULL,
	-- 部位ID
	fk_body_parts_id bigint NOT NULL,
	PRIMARY KEY (fk_user_id, fk_body_parts_id)
) WITHOUT OIDS;


-- 部位マスタ
CREATE TABLE bodyparts
(
	-- 部位ID
	body_parts_id bigserial NOT NULL,
	-- 部位名
	body_parts_name varchar(20) DEFAULT '',
	-- 部位ステータス : 使用中ならALIVE, 削除済みならDELETED
	status varchar(7) DEFAULT 'ALIVE' NOT NULL,
	-- 登録日
	created_at timestamp NOT NULL,
	-- 更新日
	updated_at timestamp NOT NULL,
	PRIMARY KEY (body_parts_id)
) WITHOUT OIDS;


-- 薬マスタ
CREATE TABLE medicine
(
	-- 薬ID
	medicine_id bigserial NOT NULL,
	-- medicine_name
	medicine_name varchar(100) DEFAULT '' NOT NULL,
	-- 薬メモ
	medicine_memo varchar(200) DEFAULT '',
	-- 薬ステータス : 使用中ならALIVE, 削除済みならDELETED
	status varchar(7) DEFAULT 'ALIVE' NOT NULL,
	-- 登録日時
	created_at timestamp NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	PRIMARY KEY (medicine_id)
) WITHOUT OIDS;


-- 痛み記録マスタ
CREATE TABLE notes
(
	-- 痛み記録ID
	note_id bigserial NOT NULL,
	-- 痛みレベル
	pain_level int DEFAULT 0 NOT NULL,
	-- メモ
	memo varchar(250) DEFAULT '' NOT NULL,
	-- 登録日時
	created_at timestamp NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	CONSTRAINT notes_pkey PRIMARY KEY (note_id)
) WITHOUT OIDS;


-- 痛み記録_部位マスタ関連テーブル
CREATE TABLE notes_bodyparts
(
	-- 痛み記録ID
	fk_note_id bigint NOT NULL,
	-- 部位ID
	fk_bodyparts_id bigint NOT NULL,
	-- 痛み記録_部位連番
	body_parts_seq int NOT NULL,
	PRIMARY KEY (fk_note_id, fk_bodyparts_id, body_parts_seq),
	UNIQUE (fk_note_id, fk_bodyparts_id, body_parts_seq)
) WITHOUT OIDS;


-- 痛み記録_薬マスタ関連テーブル
CREATE TABLE notes_medicine
(
	-- 痛み記録ID
	fk_note_id bigint NOT NULL,
	-- 薬ID
	fk_medicine_id bigint NOT NULL,
	-- 痛み記録_薬登録連番
	medicine_seq int NOT NULL,
	PRIMARY KEY (fk_note_id, fk_medicine_id, medicine_seq),
	UNIQUE (fk_note_id, fk_medicine_id, medicine_seq)
) WITHOUT OIDS;


-- ユーザーマスタ
CREATE TABLE users
(
	-- ユーザid
	user_id bigserial NOT NULL,
	-- パスワード
	password varchar(255) NOT NULL,
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
	status varchar(7) DEFAULT 'ALIVE' NOT NULL,
	-- 登録日時
	created_at timestamp NOT NULL,
	-- 更新日時
	updated_at timestamp NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
) WITHOUT OIDS;


-- ユーザー_薬マスタ関連テーブル
CREATE TABLE users_medicine
(
	-- ユーザid
	fk_user_id bigint NOT NULL,
	-- 薬ID
	fk_medicine_id bigint NOT NULL,
	PRIMARY KEY (fk_user_id, fk_medicine_id)
) WITHOUT OIDS;


-- ユーザー_痛み記録マスタ関連テーブル
CREATE TABLE users_notes
(
	-- fkユーザid
	fk_user_id bigint NOT NULL,
	-- fk番号te_id
	fk_note_id bigint NOT NULL,
	CONSTRAINT users_notes_pkey PRIMARY KEY (fk_user_id, fk_note_id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE notes_images
	ADD FOREIGN KEY (fk_images_id)
	REFERENCES images (images_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE users_bodyparts
	ADD FOREIGN KEY (fk_body_parts_id)
	REFERENCES bodyparts (body_parts_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notes_bodyparts
	ADD FOREIGN KEY (fk_bodyparts_id)
	REFERENCES bodyparts (body_parts_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notes_medicine
	ADD FOREIGN KEY (fk_medicine_id)
	REFERENCES medicine (medicine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE users_medicine
	ADD FOREIGN KEY (fk_medicine_id)
	REFERENCES medicine (medicine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notes_images
	ADD FOREIGN KEY (fk_note_id)
	REFERENCES notes (note_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notes_bodyparts
	ADD FOREIGN KEY (fk_note_id)
	REFERENCES notes (note_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notes_medicine
	ADD FOREIGN KEY (fk_note_id)
	REFERENCES notes (note_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE users_notes
	ADD CONSTRAINT users_notes_fk_note_id_fkey FOREIGN KEY (fk_note_id)
	REFERENCES notes (note_id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE users_bodyparts
	ADD FOREIGN KEY (fk_user_id)
	REFERENCES users (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE users_medicine
	ADD FOREIGN KEY (fk_user_id)
	REFERENCES users (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE users_notes
	ADD CONSTRAINT users_notes_fk_user_id_fkey FOREIGN KEY (fk_user_id)
	REFERENCES users (user_id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;



/* Comments */

COMMENT ON TABLE images IS '新規テーブル';
COMMENT ON COLUMN images.images_id IS '部位画像ID';
COMMENT ON COLUMN images.images_path IS '部位画像パス';
COMMENT ON COLUMN images.updated_at IS '更新日時';
COMMENT ON COLUMN images.created_at IS '登録日時';
COMMENT ON TABLE notes_images IS '新規テーブル';
COMMENT ON COLUMN notes_images.fk_note_id IS '痛み記録ID';
COMMENT ON COLUMN notes_images.fk_images_id IS '部位画像ID';
COMMENT ON COLUMN notes_images.images_seq IS '画像連番';
COMMENT ON TABLE users_bodyparts IS '新規テーブル';
COMMENT ON COLUMN users_bodyparts.fk_user_id IS 'ユーザid';
COMMENT ON COLUMN users_bodyparts.fk_body_parts_id IS '部位ID';
COMMENT ON TABLE bodyparts IS '部位マスタ';
COMMENT ON COLUMN bodyparts.body_parts_id IS '部位ID';
COMMENT ON COLUMN bodyparts.body_parts_name IS '部位名';
COMMENT ON COLUMN bodyparts.status IS '部位ステータス : 使用中ならALIVE, 削除済みならDELETED';
COMMENT ON COLUMN bodyparts.created_at IS '登録日';
COMMENT ON COLUMN bodyparts.updated_at IS '更新日';
COMMENT ON TABLE medicine IS '薬マスタ';
COMMENT ON COLUMN medicine.medicine_id IS '薬ID';
COMMENT ON COLUMN medicine.medicine_name IS 'medicine_name';
COMMENT ON COLUMN medicine.medicine_memo IS '薬メモ';
COMMENT ON COLUMN medicine.status IS '薬ステータス : 使用中ならALIVE, 削除済みならDELETED';
COMMENT ON COLUMN medicine.created_at IS '登録日時';
COMMENT ON COLUMN medicine.updated_at IS '更新日時';
COMMENT ON TABLE notes IS '痛み記録マスタ';
COMMENT ON COLUMN notes.note_id IS '痛み記録ID';
COMMENT ON COLUMN notes.pain_level IS '痛みレベル';
COMMENT ON COLUMN notes.memo IS 'メモ';
COMMENT ON COLUMN notes.created_at IS '登録日時';
COMMENT ON COLUMN notes.updated_at IS '更新日時';
COMMENT ON TABLE notes_bodyparts IS '痛み記録_部位マスタ関連テーブル';
COMMENT ON COLUMN notes_bodyparts.fk_note_id IS '痛み記録ID';
COMMENT ON COLUMN notes_bodyparts.fk_bodyparts_id IS '部位ID';
COMMENT ON COLUMN notes_bodyparts.body_parts_seq IS '痛み記録_部位連番';
COMMENT ON TABLE notes_medicine IS '痛み記録_薬マスタ関連テーブル';
COMMENT ON COLUMN notes_medicine.fk_note_id IS '痛み記録ID';
COMMENT ON COLUMN notes_medicine.fk_medicine_id IS '薬ID';
COMMENT ON COLUMN notes_medicine.medicine_seq IS '痛み記録_薬登録連番';
COMMENT ON TABLE users IS 'ユーザーマスタ';
COMMENT ON COLUMN users.user_id IS 'ユーザid';
COMMENT ON COLUMN users.password IS 'パスワード';
COMMENT ON COLUMN users.user_name IS 'ユーザ名';
COMMENT ON COLUMN users.email IS 'Eメール';
COMMENT ON COLUMN users.birthday IS 'birthday';
COMMENT ON COLUMN users.age IS 'age';
COMMENT ON COLUMN users.role IS 'ロール';
COMMENT ON COLUMN users.status IS 'ユーザーステータス : 使用中ならALIVE, 削除済みならDELETED';
COMMENT ON COLUMN users.created_at IS '登録日時';
COMMENT ON COLUMN users.updated_at IS '更新日時';
COMMENT ON TABLE users_medicine IS 'ユーザー_薬マスタ関連テーブル';
COMMENT ON COLUMN users_medicine.fk_user_id IS 'ユーザid';
COMMENT ON COLUMN users_medicine.fk_medicine_id IS '薬ID';
COMMENT ON TABLE users_notes IS 'ユーザー_痛み記録マスタ関連テーブル';
COMMENT ON COLUMN users_notes.fk_user_id IS 'fkユーザid';
COMMENT ON COLUMN users_notes.fk_note_id IS 'fk番号te_id';



