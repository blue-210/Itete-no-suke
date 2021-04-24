/*
 * This file is generated by jOOQ.
 */
package app.itetenosuke.infra.db.jooq.generated.tables.records;


import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_IMAGES_TABLE;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 新規テーブル
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotesImagesRecord extends UpdatableRecordImpl<NotesImagesRecord> implements Record3<Long, Long, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sukeroku.notes_images.fk_note_id</code>. 痛み記録ID
     */
    public void setFkNoteId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>sukeroku.notes_images.fk_note_id</code>. 痛み記録ID
     */
    public Long getFkNoteId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>sukeroku.notes_images.fk_images_id</code>. 部位画像ID
     */
    public void setFkImagesId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>sukeroku.notes_images.fk_images_id</code>. 部位画像ID
     */
    public Long getFkImagesId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>sukeroku.notes_images.images_seq</code>. 画像連番
     */
    public void setImagesSeq(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>sukeroku.notes_images.images_seq</code>. 画像連番
     */
    public Integer getImagesSeq() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Long, Long, Integer> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return NOTES_IMAGES_TABLE.NOTES_IMAGES.FK_NOTE_ID;
    }

    @Override
    public Field<Long> field2() {
        return NOTES_IMAGES_TABLE.NOTES_IMAGES.FK_IMAGES_ID;
    }

    @Override
    public Field<Integer> field3() {
        return NOTES_IMAGES_TABLE.NOTES_IMAGES.IMAGES_SEQ;
    }

    @Override
    public Long component1() {
        return getFkNoteId();
    }

    @Override
    public Long component2() {
        return getFkImagesId();
    }

    @Override
    public Integer component3() {
        return getImagesSeq();
    }

    @Override
    public Long value1() {
        return getFkNoteId();
    }

    @Override
    public Long value2() {
        return getFkImagesId();
    }

    @Override
    public Integer value3() {
        return getImagesSeq();
    }

    @Override
    public NotesImagesRecord value1(Long value) {
        setFkNoteId(value);
        return this;
    }

    @Override
    public NotesImagesRecord value2(Long value) {
        setFkImagesId(value);
        return this;
    }

    @Override
    public NotesImagesRecord value3(Integer value) {
        setImagesSeq(value);
        return this;
    }

    @Override
    public NotesImagesRecord values(Long value1, Long value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NotesImagesRecord
     */
    public NotesImagesRecord() {
        super(NOTES_IMAGES_TABLE.NOTES_IMAGES);
    }

    /**
     * Create a detached, initialised NotesImagesRecord
     */
    public NotesImagesRecord(Long fkNoteId, Long fkImagesId, Integer imagesSeq) {
        super(NOTES_IMAGES_TABLE.NOTES_IMAGES);

        setFkNoteId(fkNoteId);
        setFkImagesId(fkImagesId);
        setImagesSeq(imagesSeq);
    }
}