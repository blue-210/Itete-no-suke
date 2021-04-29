CREATE TABLE IF NOT EXISTS sukeroku.pain_records (
   painrecord_id int8 NOT NULL,
   pain_level int4 NOT NULL,
   memo varchar(250) NOT NULL,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   CONSTRAINT notes_pkey_1 PRIMARY KEY (painrecord_id)
);
