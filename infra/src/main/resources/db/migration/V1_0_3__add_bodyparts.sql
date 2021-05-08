-- DROP TABLE sukeroku.bodyparts;
CREATE TABLE IF NOT EXISTS sukeroku.body_parts (
   body_part_id char(36) NOT NULL,
   user_id char(36) NOT NULL,
   body_part_name varchar(20) NULL DEFAULT '' :: character varying,
   STATUS varchar(7) NOT NULL DEFAULT 'ALIVE' :: character varying,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   CONSTRAINT body_parts_pkey PRIMARY KEY (body_part_id)
);

-- DROP TABLE sukeroku.body_parts_enrollments;
CREATE TABLE IF NOT EXISTS sukeroku.body_parts_enrollments (
   pain_record_id char(36) NOT NULL,
   body_part_id char(36) NOT NULL,
   body_part_seq int4 NOT NULL,
   CONSTRAINT body_parts_enrollments_pkey PRIMARY KEY (pain_record_id, body_part_id, body_part_seq)
);
