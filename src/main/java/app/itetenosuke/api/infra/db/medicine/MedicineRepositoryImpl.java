package app.itetenosuke.api.infra.db.medicine;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.medicine.IMedicineRepository;
import app.itetenosuke.api.domain.medicine.Medicine;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.MEDICINE_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.PAINRECORDS_MEDICINE_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.PAIN_RECORDS_TABLE;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public class MedicineRepositoryImpl implements IMedicineRepository {
  private final DSLContext create;
  private static final MEDICINE_TABLE M = MEDICINE_TABLE.MEDICINE.as("M");
  private static final PAINRECORDS_MEDICINE_TABLE PM =
      PAINRECORDS_MEDICINE_TABLE.PAINRECORDS_MEDICINE.as("PM");
  private static final PAIN_RECORDS_TABLE P = PAIN_RECORDS_TABLE.PAIN_RECORDS.as("P");

  @Override
  @Transactional
  public boolean updateMedicineRecords(PainRecord painRecord) {
    Integer resultCount = -1;
    try {
      resultCount =
          painRecord
              .getMedicineList()
              .stream()
              .mapToInt(
                  medicine -> {
                    Integer updateCount =
                        create
                            .update(M)
                            .set(M.MEDICINE_NAME, medicine.getMedicineName())
                            .set(M.MEDICINE_MEMO, medicine.getMedicineMemo())
                            .set(M.STATUS, medicine.getStatus())
                            .set(M.UPDATED_AT, medicine.getUpdatedAt())
                            .where(M.MEDICINE_ID.eq(medicine.getMedicineId()))
                            .execute();

                    log.info(
                        "update a medicine record : count={}, pain_record_id={}, medicine_id={}",
                        updateCount,
                        painRecord.getPainRecordId(),
                        medicine.getMedicineId());

                    Integer updateJunctionCount =
                        create
                            .update(PM)
                            .set(PM.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(PM.MEDICINE_ID, medicine.getMedicineId())
                            .set(PM.MEDICINE_SEQ, medicine.getMedicineSeq())
                            .where(PM.PAIN_RECORD_ID.eq(painRecord.getPainRecordId()))
                            .and(PM.MEDICINE_ID.eq(medicine.getMedicineId()))
                            .and(PM.MEDICINE_SEQ.eq(medicine.getMedicineSeq()))
                            .execute();

                    log.info(
                        "update a painrecord_medicine record : count={}, pain_record_id={}, medicine_id={}",
                        updateCount,
                        painRecord.getPainRecordId(),
                        medicine.getMedicineId());

                    return updateCount + updateJunctionCount;
                  })
              .sum();
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return resultCount > 0 ? true : false;
  }

  @Override
  @Transactional
  public boolean exists(Medicine medicine, String painRecordId) {
    return create.fetchExists(
        create
            .selectOne()
            .from(M)
            .join(PM)
            .on(PM.MEDICINE_ID.eq(M.MEDICINE_ID))
            .join(P)
            .on(P.PAIN_RECORD_ID.eq(PM.PAIN_RECORD_ID))
            .where(M.MEDICINE_ID.eq(medicine.getMedicineId()))
            .and(P.PAIN_RECORD_ID.eq(painRecordId)));
  }

  @Override
  @Transactional
  public boolean createMedicineRecords(PainRecord painRecord) {
    Integer resultCount = -1;
    try {
      resultCount =
          painRecord
              .getMedicineList()
              .stream()
              .mapToInt(
                  medicine -> {
                    Integer insertCount =
                        create
                            .insertInto(M)
                            .set(M.MEDICINE_ID, medicine.getMedicineId())
                            .set(M.MEDICINE_NAME, medicine.getMedicineName())
                            .set(M.MEDICINE_MEMO, medicine.getMedicineMemo())
                            .set(M.CREATED_AT, medicine.getCreatedAt())
                            .set(M.UPDATED_AT, medicine.getUpdatedAt())
                            .execute();
                    // TODO ログ出力

                    Integer insertJunctionCount =
                        create
                            .insertInto(PM)
                            .set(PM.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(PM.MEDICINE_ID, medicine.getMedicineId())
                            .set(PM.MEDICINE_SEQ, medicine.getMedicineSeq())
                            .execute();
                    return insertCount + insertJunctionCount;
                  })
              .sum();
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return resultCount > 0 ? true : false;
  }
}
