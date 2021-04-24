/*
 * This file is generated by jOOQ.
 */
package app.itetenosuke.infra.db.jooq.generated;


import app.itetenosuke.infra.db.jooq.generated.tables.BODYPARTS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.FLYWAY_SCHEMA_HISTORY_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.IMAGES_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.MEDICINE_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_BODYPARTS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_IMAGES_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_MEDICINE_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.PAIN_RECORDS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.USERS_BODYPARTS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.USERS_MEDICINE_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.USERS_NOTES_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.USERS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.records.BodypartsRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.FlywaySchemaHistoryRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.ImagesRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.MedicineRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.NotesBodypartsRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.NotesImagesRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.NotesMedicineRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.NotesRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.PainRecordsRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.UsersBodypartsRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.UsersMedicineRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.UsersNotesRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.records.UsersRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * sukeroku.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<BodypartsRecord> BODYPARTS_PKEY = Internal.createUniqueKey(BODYPARTS_TABLE.BODYPARTS, DSL.name("bodyparts_pkey"), new TableField[] { BODYPARTS_TABLE.BODYPARTS.BODY_PARTS_ID }, true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FLYWAY_SCHEMA_HISTORY_TABLE.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FLYWAY_SCHEMA_HISTORY_TABLE.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<ImagesRecord> IMAGES_PKEY = Internal.createUniqueKey(IMAGES_TABLE.IMAGES, DSL.name("images_pkey"), new TableField[] { IMAGES_TABLE.IMAGES.IMAGES_ID }, true);
    public static final UniqueKey<MedicineRecord> MEDICINE_PKEY = Internal.createUniqueKey(MEDICINE_TABLE.MEDICINE, DSL.name("medicine_pkey"), new TableField[] { MEDICINE_TABLE.MEDICINE.MEDICINE_ID }, true);
    public static final UniqueKey<NotesRecord> NOTES_PKEY = Internal.createUniqueKey(NOTES_TABLE.NOTES, DSL.name("notes_pkey"), new TableField[] { NOTES_TABLE.NOTES.NOTE_ID }, true);
    public static final UniqueKey<NotesBodypartsRecord> NOTES_BODYPARTS_PKEY = Internal.createUniqueKey(NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS, DSL.name("notes_bodyparts_pkey"), new TableField[] { NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS.FK_NOTE_ID, NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS.FK_BODYPARTS_ID, NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS.BODY_PARTS_SEQ }, true);
    public static final UniqueKey<NotesImagesRecord> NOTES_IMAGES_PKEY = Internal.createUniqueKey(NOTES_IMAGES_TABLE.NOTES_IMAGES, DSL.name("notes_images_pkey"), new TableField[] { NOTES_IMAGES_TABLE.NOTES_IMAGES.FK_NOTE_ID, NOTES_IMAGES_TABLE.NOTES_IMAGES.FK_IMAGES_ID, NOTES_IMAGES_TABLE.NOTES_IMAGES.IMAGES_SEQ }, true);
    public static final UniqueKey<NotesMedicineRecord> NOTES_MEDICINE_PKEY = Internal.createUniqueKey(NOTES_MEDICINE_TABLE.NOTES_MEDICINE, DSL.name("notes_medicine_pkey"), new TableField[] { NOTES_MEDICINE_TABLE.NOTES_MEDICINE.FK_NOTE_ID, NOTES_MEDICINE_TABLE.NOTES_MEDICINE.FK_MEDICINE_ID, NOTES_MEDICINE_TABLE.NOTES_MEDICINE.MEDICINE_SEQ }, true);
    public static final UniqueKey<PainRecordsRecord> NOTES_PKEY_1 = Internal.createUniqueKey(PAIN_RECORDS_TABLE.PAIN_RECORDS, DSL.name("notes_pkey_1"), new TableField[] { PAIN_RECORDS_TABLE.PAIN_RECORDS.PAINRECORD_ID }, true);
    public static final UniqueKey<UsersRecord> USERS_EMAIL_KEY = Internal.createUniqueKey(USERS_TABLE.USERS, DSL.name("users_email_key"), new TableField[] { USERS_TABLE.USERS.EMAIL }, true);
    public static final UniqueKey<UsersRecord> USERS_PKEY = Internal.createUniqueKey(USERS_TABLE.USERS, DSL.name("users_pkey"), new TableField[] { USERS_TABLE.USERS.USER_ID }, true);
    public static final UniqueKey<UsersBodypartsRecord> USERS_BODYPARTS_PKEY = Internal.createUniqueKey(USERS_BODYPARTS_TABLE.USERS_BODYPARTS, DSL.name("users_bodyparts_pkey"), new TableField[] { USERS_BODYPARTS_TABLE.USERS_BODYPARTS.FK_USER_ID, USERS_BODYPARTS_TABLE.USERS_BODYPARTS.FK_BODY_PARTS_ID }, true);
    public static final UniqueKey<UsersMedicineRecord> USERS_MEDICINE_PKEY = Internal.createUniqueKey(USERS_MEDICINE_TABLE.USERS_MEDICINE, DSL.name("users_medicine_pkey"), new TableField[] { USERS_MEDICINE_TABLE.USERS_MEDICINE.FK_USER_ID, USERS_MEDICINE_TABLE.USERS_MEDICINE.FK_MEDICINE_ID }, true);
    public static final UniqueKey<UsersNotesRecord> USERS_NOTES_PKEY = Internal.createUniqueKey(USERS_NOTES_TABLE.USERS_NOTES, DSL.name("users_notes_pkey"), new TableField[] { USERS_NOTES_TABLE.USERS_NOTES.FK_USER_ID, USERS_NOTES_TABLE.USERS_NOTES.FK_NOTE_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<NotesBodypartsRecord, BodypartsRecord> NOTES_BODYPARTS__NOTES_BODYPARTS_FK_BODYPARTS_ID_FKEY = Internal.createForeignKey(NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS, DSL.name("notes_bodyparts_fk_bodyparts_id_fkey"), new TableField[] { NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS.FK_BODYPARTS_ID }, Keys.BODYPARTS_PKEY, new TableField[] { BODYPARTS_TABLE.BODYPARTS.BODY_PARTS_ID }, true);
    public static final ForeignKey<NotesBodypartsRecord, NotesRecord> NOTES_BODYPARTS__NOTES_BODYPARTS_FK_NOTE_ID_FKEY = Internal.createForeignKey(NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS, DSL.name("notes_bodyparts_fk_note_id_fkey"), new TableField[] { NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS.FK_NOTE_ID }, Keys.NOTES_PKEY, new TableField[] { NOTES_TABLE.NOTES.NOTE_ID }, true);
    public static final ForeignKey<NotesImagesRecord, ImagesRecord> NOTES_IMAGES__NOTES_IMAGES_FK_IMAGES_ID_FKEY = Internal.createForeignKey(NOTES_IMAGES_TABLE.NOTES_IMAGES, DSL.name("notes_images_fk_images_id_fkey"), new TableField[] { NOTES_IMAGES_TABLE.NOTES_IMAGES.FK_IMAGES_ID }, Keys.IMAGES_PKEY, new TableField[] { IMAGES_TABLE.IMAGES.IMAGES_ID }, true);
    public static final ForeignKey<NotesImagesRecord, NotesRecord> NOTES_IMAGES__NOTES_IMAGES_FK_NOTE_ID_FKEY = Internal.createForeignKey(NOTES_IMAGES_TABLE.NOTES_IMAGES, DSL.name("notes_images_fk_note_id_fkey"), new TableField[] { NOTES_IMAGES_TABLE.NOTES_IMAGES.FK_NOTE_ID }, Keys.NOTES_PKEY, new TableField[] { NOTES_TABLE.NOTES.NOTE_ID }, true);
    public static final ForeignKey<NotesMedicineRecord, MedicineRecord> NOTES_MEDICINE__NOTES_MEDICINE_FK_MEDICINE_ID_FKEY = Internal.createForeignKey(NOTES_MEDICINE_TABLE.NOTES_MEDICINE, DSL.name("notes_medicine_fk_medicine_id_fkey"), new TableField[] { NOTES_MEDICINE_TABLE.NOTES_MEDICINE.FK_MEDICINE_ID }, Keys.MEDICINE_PKEY, new TableField[] { MEDICINE_TABLE.MEDICINE.MEDICINE_ID }, true);
    public static final ForeignKey<NotesMedicineRecord, NotesRecord> NOTES_MEDICINE__NOTES_MEDICINE_FK_NOTE_ID_FKEY = Internal.createForeignKey(NOTES_MEDICINE_TABLE.NOTES_MEDICINE, DSL.name("notes_medicine_fk_note_id_fkey"), new TableField[] { NOTES_MEDICINE_TABLE.NOTES_MEDICINE.FK_NOTE_ID }, Keys.NOTES_PKEY, new TableField[] { NOTES_TABLE.NOTES.NOTE_ID }, true);
    public static final ForeignKey<UsersBodypartsRecord, BodypartsRecord> USERS_BODYPARTS__USERS_BODYPARTS_FK_BODY_PARTS_ID_FKEY = Internal.createForeignKey(USERS_BODYPARTS_TABLE.USERS_BODYPARTS, DSL.name("users_bodyparts_fk_body_parts_id_fkey"), new TableField[] { USERS_BODYPARTS_TABLE.USERS_BODYPARTS.FK_BODY_PARTS_ID }, Keys.BODYPARTS_PKEY, new TableField[] { BODYPARTS_TABLE.BODYPARTS.BODY_PARTS_ID }, true);
    public static final ForeignKey<UsersBodypartsRecord, UsersRecord> USERS_BODYPARTS__USERS_BODYPARTS_FK_USER_ID_FKEY = Internal.createForeignKey(USERS_BODYPARTS_TABLE.USERS_BODYPARTS, DSL.name("users_bodyparts_fk_user_id_fkey"), new TableField[] { USERS_BODYPARTS_TABLE.USERS_BODYPARTS.FK_USER_ID }, Keys.USERS_PKEY, new TableField[] { USERS_TABLE.USERS.USER_ID }, true);
    public static final ForeignKey<UsersMedicineRecord, MedicineRecord> USERS_MEDICINE__USERS_MEDICINE_FK_MEDICINE_ID_FKEY = Internal.createForeignKey(USERS_MEDICINE_TABLE.USERS_MEDICINE, DSL.name("users_medicine_fk_medicine_id_fkey"), new TableField[] { USERS_MEDICINE_TABLE.USERS_MEDICINE.FK_MEDICINE_ID }, Keys.MEDICINE_PKEY, new TableField[] { MEDICINE_TABLE.MEDICINE.MEDICINE_ID }, true);
    public static final ForeignKey<UsersMedicineRecord, UsersRecord> USERS_MEDICINE__USERS_MEDICINE_FK_USER_ID_FKEY = Internal.createForeignKey(USERS_MEDICINE_TABLE.USERS_MEDICINE, DSL.name("users_medicine_fk_user_id_fkey"), new TableField[] { USERS_MEDICINE_TABLE.USERS_MEDICINE.FK_USER_ID }, Keys.USERS_PKEY, new TableField[] { USERS_TABLE.USERS.USER_ID }, true);
    public static final ForeignKey<UsersNotesRecord, NotesRecord> USERS_NOTES__USERS_NOTES_FK_NOTE_ID_FKEY = Internal.createForeignKey(USERS_NOTES_TABLE.USERS_NOTES, DSL.name("users_notes_fk_note_id_fkey"), new TableField[] { USERS_NOTES_TABLE.USERS_NOTES.FK_NOTE_ID }, Keys.NOTES_PKEY, new TableField[] { NOTES_TABLE.NOTES.NOTE_ID }, true);
    public static final ForeignKey<UsersNotesRecord, UsersRecord> USERS_NOTES__USERS_NOTES_FK_USER_ID_FKEY = Internal.createForeignKey(USERS_NOTES_TABLE.USERS_NOTES, DSL.name("users_notes_fk_user_id_fkey"), new TableField[] { USERS_NOTES_TABLE.USERS_NOTES.FK_USER_ID }, Keys.USERS_PKEY, new TableField[] { USERS_TABLE.USERS.USER_ID }, true);
}