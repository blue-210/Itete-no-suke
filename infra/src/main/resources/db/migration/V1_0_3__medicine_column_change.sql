CREATE TABLE sukeroku.medicine (
   medicine_id char(36) NOT NULL,
   medicine_name varchar(100) NOT NULL DEFAULT '' :: character varying,
   medicine_memo varchar(200) NULL DEFAULT '' :: character varying,
   STATUS varchar(7) NOT NULL DEFAULT 'ALIVE' :: character varying,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   CONSTRAINT medicine_pkey PRIMARY KEY (medicine_id)
);

-- sukeroku.painrecords_medicine definition
-- Drop table
CREATE TABLE sukeroku.MEDICINE_ENROLLMENTS (
   pain_record_id char(36) NOT NULL,
   medicine_id char(36) NOT NULL,
   medicine_seq int4 NOT NULL,
   CONSTRAINT medicine_enrollments_pkey PRIMARY KEY (pain_record_id, medicine_id, medicine_seq)
);
