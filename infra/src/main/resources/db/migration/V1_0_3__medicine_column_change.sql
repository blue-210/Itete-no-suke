-- sukeroku.medicine definition
-- Drop table
DROP TABLE sukeroku.medicine CASCADE;
CREATE TABLE sukeroku.medicine (
   medicine_id char(36) NOT NULL,
   medicine_name varchar(100) NOT NULL DEFAULT ''::character varying,
   medicine_memo varchar(200) NULL DEFAULT ''::character varying,
   status varchar(7) NOT NULL DEFAULT 'ALIVE'::character varying,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   CONSTRAINT medicine_pkey PRIMARY KEY (medicine_id)
);
-- sukeroku.painrecords_medicine definition
-- Drop table
CREATE TABLE IF NOT EXISTS sukeroku.painrecords_medicine (
   pain_record_id char(36) NOT NULL,
   medicine_id char(36) NOT NULL,
   medicine_seq int4 NOT NULL,
   CONSTRAINT painrecords_medicine_pkey PRIMARY KEY (pain_record_id, medicine_id, medicine_seq)
);
