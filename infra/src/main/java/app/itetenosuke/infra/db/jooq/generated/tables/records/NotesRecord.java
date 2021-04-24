/*
 * This file is generated by jOOQ.
 */
package app.itetenosuke.infra.db.jooq.generated.tables.records;


import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_TABLE;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 痛み記録マスタ
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotesRecord extends UpdatableRecordImpl<NotesRecord> implements Record5<Long, Integer, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sukeroku.notes.note_id</code>. 痛み記録ID
     */
    public void setNoteId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>sukeroku.notes.note_id</code>. 痛み記録ID
     */
    public Long getNoteId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>sukeroku.notes.pain_level</code>. 痛みレベル
     */
    public void setPainLevel(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>sukeroku.notes.pain_level</code>. 痛みレベル
     */
    public Integer getPainLevel() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>sukeroku.notes.memo</code>. メモ
     */
    public void setMemo(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>sukeroku.notes.memo</code>. メモ
     */
    public String getMemo() {
        return (String) get(2);
    }

    /**
     * Setter for <code>sukeroku.notes.created_at</code>. 登録日時
     */
    public void setCreatedAt(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>sukeroku.notes.created_at</code>. 登録日時
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>sukeroku.notes.updated_at</code>. 更新日時
     */
    public void setUpdatedAt(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>sukeroku.notes.updated_at</code>. 更新日時
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Integer, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Integer, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return NOTES_TABLE.NOTES.NOTE_ID;
    }

    @Override
    public Field<Integer> field2() {
        return NOTES_TABLE.NOTES.PAIN_LEVEL;
    }

    @Override
    public Field<String> field3() {
        return NOTES_TABLE.NOTES.MEMO;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return NOTES_TABLE.NOTES.CREATED_AT;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return NOTES_TABLE.NOTES.UPDATED_AT;
    }

    @Override
    public Long component1() {
        return getNoteId();
    }

    @Override
    public Integer component2() {
        return getPainLevel();
    }

    @Override
    public String component3() {
        return getMemo();
    }

    @Override
    public LocalDateTime component4() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime component5() {
        return getUpdatedAt();
    }

    @Override
    public Long value1() {
        return getNoteId();
    }

    @Override
    public Integer value2() {
        return getPainLevel();
    }

    @Override
    public String value3() {
        return getMemo();
    }

    @Override
    public LocalDateTime value4() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime value5() {
        return getUpdatedAt();
    }

    @Override
    public NotesRecord value1(Long value) {
        setNoteId(value);
        return this;
    }

    @Override
    public NotesRecord value2(Integer value) {
        setPainLevel(value);
        return this;
    }

    @Override
    public NotesRecord value3(String value) {
        setMemo(value);
        return this;
    }

    @Override
    public NotesRecord value4(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public NotesRecord value5(LocalDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public NotesRecord values(Long value1, Integer value2, String value3, LocalDateTime value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NotesRecord
     */
    public NotesRecord() {
        super(NOTES_TABLE.NOTES);
    }

    /**
     * Create a detached, initialised NotesRecord
     */
    public NotesRecord(Long noteId, Integer painLevel, String memo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(NOTES_TABLE.NOTES);

        setNoteId(noteId);
        setPainLevel(painLevel);
        setMemo(memo);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }
}