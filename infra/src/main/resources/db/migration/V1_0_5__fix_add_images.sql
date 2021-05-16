-- DROP TABLE sukeroku.bodyparts;
CREATE TABLE IF NOT EXISTS sukeroku.images (
   image_id char(36) NOT NULL,
   user_id char(36) NOT NULL,
   image_path varchar(200) NULL DEFAULT '' :: character varying,
   STATUS varchar(7) NOT NULL DEFAULT 'ALIVE' :: character varying,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   CONSTRAINT images_pkey PRIMARY KEY (image_id)
);

-- DROP TABLE sukeroku.body_parts_enrollments;
CREATE TABLE IF NOT EXISTS sukeroku.images_enrollments (
   pain_record_id char(36) NOT NULL,
   image_id char(36) NOT NULL,
   image_seq int4 NOT NULL,
   CONSTRAINT images_enrollments_pkey PRIMARY KEY (pain_record_id, image_id, image_seq)
);
