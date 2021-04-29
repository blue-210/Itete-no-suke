create role sukeroku with login password ‘ D23iKlso3iqoiad ’;
— DB作成 drop database Itete - no - suke;
create database itetenosuke owner sukeroku — publicは削除 drop schema public;
create schema sukeroku authorization sukeroku;
CREATE TABLE IF NOT EXISTS pain_records (
   painrecord_id char(36) NOT NULL,
   pain_level int4 NOT NULL,
   memo varchar(250) NOT NULL,
   created_at timestamp NOT NULL,
   updated_at timestamp NOT NULL,
   CONSTRAINT notes_pkey_1 PRIMARY KEY (painrecord_id)
) WITHOUT OIDS;
