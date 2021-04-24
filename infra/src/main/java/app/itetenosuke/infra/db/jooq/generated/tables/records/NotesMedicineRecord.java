/*
 * This file is generated by jOOQ.
 */
package app.itetenosuke.infra.db.jooq.generated.tables.records;


import app.itetenosuke.infra.db.jooq.generated.tables.NOTES_MEDICINE_TABLE;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 痛み記録_薬マスタ関連テーブル
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotesMedicineRecord extends UpdatableRecordImpl<NotesMedicineRecord> implements Record3<Long, Long, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sukeroku.notes_medicine.fk_note_id</code>. 痛み記録ID
     */
    public void setFkNoteId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>sukeroku.notes_medicine.fk_note_id</code>. 痛み記録ID
     */
    public Long getFkNoteId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>sukeroku.notes_medicine.fk_medicine_id</code>. 薬ID
     */
    public void setFkMedicineId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>sukeroku.notes_medicine.fk_medicine_id</code>. 薬ID
     */
    public Long getFkMedicineId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>sukeroku.notes_medicine.medicine_seq</code>. 痛み記録_薬登録連番
     */
    public void setMedicineSeq(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>sukeroku.notes_medicine.medicine_seq</code>. 痛み記録_薬登録連番
     */
    public Integer getMedicineSeq() {
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
        return NOTES_MEDICINE_TABLE.NOTES_MEDICINE.FK_NOTE_ID;
    }

    @Override
    public Field<Long> field2() {
        return NOTES_MEDICINE_TABLE.NOTES_MEDICINE.FK_MEDICINE_ID;
    }

    @Override
    public Field<Integer> field3() {
        return NOTES_MEDICINE_TABLE.NOTES_MEDICINE.MEDICINE_SEQ;
    }

    @Override
    public Long component1() {
        return getFkNoteId();
    }

    @Override
    public Long component2() {
        return getFkMedicineId();
    }

    @Override
    public Integer component3() {
        return getMedicineSeq();
    }

    @Override
    public Long value1() {
        return getFkNoteId();
    }

    @Override
    public Long value2() {
        return getFkMedicineId();
    }

    @Override
    public Integer value3() {
        return getMedicineSeq();
    }

    @Override
    public NotesMedicineRecord value1(Long value) {
        setFkNoteId(value);
        return this;
    }

    @Override
    public NotesMedicineRecord value2(Long value) {
        setFkMedicineId(value);
        return this;
    }

    @Override
    public NotesMedicineRecord value3(Integer value) {
        setMedicineSeq(value);
        return this;
    }

    @Override
    public NotesMedicineRecord values(Long value1, Long value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NotesMedicineRecord
     */
    public NotesMedicineRecord() {
        super(NOTES_MEDICINE_TABLE.NOTES_MEDICINE);
    }

    /**
     * Create a detached, initialised NotesMedicineRecord
     */
    public NotesMedicineRecord(Long fkNoteId, Long fkMedicineId, Integer medicineSeq) {
        super(NOTES_MEDICINE_TABLE.NOTES_MEDICINE);

        setFkNoteId(fkNoteId);
        setFkMedicineId(fkMedicineId);
        setMedicineSeq(medicineSeq);
    }
}