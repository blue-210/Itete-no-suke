CREATE schema sukeroku;

CREATE TABLE sukeroku.pain_records (
   pain_record_id char(36) NOT NULL,
   pain_level int4 NOT NULL,
   memo varchar(250) NOT NULL,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   user_id char(36) NULL,
   CONSTRAINT notes_pkey_1 PRIMARY KEY (pain_record_id)
);
