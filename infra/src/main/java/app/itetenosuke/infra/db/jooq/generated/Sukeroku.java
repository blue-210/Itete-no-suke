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

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sukeroku extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sukeroku</code>
     */
    public static final Sukeroku SUKEROKU = new Sukeroku();

    /**
     * 部位マスタ
     */
    public final BODYPARTS_TABLE BODYPARTS = BODYPARTS_TABLE.BODYPARTS;

    /**
     * The table <code>sukeroku.flyway_schema_history</code>.
     */
    public final FLYWAY_SCHEMA_HISTORY_TABLE FLYWAY_SCHEMA_HISTORY = FLYWAY_SCHEMA_HISTORY_TABLE.FLYWAY_SCHEMA_HISTORY;

    /**
     * 新規テーブル
     */
    public final IMAGES_TABLE IMAGES = IMAGES_TABLE.IMAGES;

    /**
     * 薬マスタ
     */
    public final MEDICINE_TABLE MEDICINE = MEDICINE_TABLE.MEDICINE;

    /**
     * 痛み記録マスタ
     */
    public final NOTES_TABLE NOTES = NOTES_TABLE.NOTES;

    /**
     * 痛み記録_部位マスタ関連テーブル
     */
    public final NOTES_BODYPARTS_TABLE NOTES_BODYPARTS = NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS;

    /**
     * 新規テーブル
     */
    public final NOTES_IMAGES_TABLE NOTES_IMAGES = NOTES_IMAGES_TABLE.NOTES_IMAGES;

    /**
     * 痛み記録_薬マスタ関連テーブル
     */
    public final NOTES_MEDICINE_TABLE NOTES_MEDICINE = NOTES_MEDICINE_TABLE.NOTES_MEDICINE;

    /**
     * The table <code>sukeroku.pain_records</code>.
     */
    public final PAIN_RECORDS_TABLE PAIN_RECORDS = PAIN_RECORDS_TABLE.PAIN_RECORDS;

    /**
     * ユーザーマスタ
     */
    public final USERS_TABLE USERS = USERS_TABLE.USERS;

    /**
     * 新規テーブル
     */
    public final USERS_BODYPARTS_TABLE USERS_BODYPARTS = USERS_BODYPARTS_TABLE.USERS_BODYPARTS;

    /**
     * ユーザー_薬マスタ関連テーブル
     */
    public final USERS_MEDICINE_TABLE USERS_MEDICINE = USERS_MEDICINE_TABLE.USERS_MEDICINE;

    /**
     * ユーザー_痛み記録マスタ関連テーブル
     */
    public final USERS_NOTES_TABLE USERS_NOTES = USERS_NOTES_TABLE.USERS_NOTES;

    /**
     * No further instances allowed
     */
    private Sukeroku() {
        super("sukeroku", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.BODYPARTS_BODY_PARTS_ID_SEQ,
            Sequences.IMAGES_IMAGES_ID_SEQ,
            Sequences.MEDICINE_MEDICINE_ID_SEQ,
            Sequences.NOTES_NOTE_ID_SEQ,
            Sequences.PAIN_RECORD_PK_SEQ,
            Sequences.PAIN_RECORDS_PAINRECORD_ID_SEQ,
            Sequences.USERS_USER_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            BODYPARTS_TABLE.BODYPARTS,
            FLYWAY_SCHEMA_HISTORY_TABLE.FLYWAY_SCHEMA_HISTORY,
            IMAGES_TABLE.IMAGES,
            MEDICINE_TABLE.MEDICINE,
            NOTES_TABLE.NOTES,
            NOTES_BODYPARTS_TABLE.NOTES_BODYPARTS,
            NOTES_IMAGES_TABLE.NOTES_IMAGES,
            NOTES_MEDICINE_TABLE.NOTES_MEDICINE,
            PAIN_RECORDS_TABLE.PAIN_RECORDS,
            USERS_TABLE.USERS,
            USERS_BODYPARTS_TABLE.USERS_BODYPARTS,
            USERS_MEDICINE_TABLE.USERS_MEDICINE,
            USERS_NOTES_TABLE.USERS_NOTES);
    }
}