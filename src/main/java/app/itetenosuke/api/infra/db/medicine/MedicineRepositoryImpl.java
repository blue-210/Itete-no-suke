package app.itetenosuke.api.infra.db.medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.medicine.IMedicineRepository;
import app.itetenosuke.api.domain.medicine.Medicine;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.infra.db.jooq.generated.tables.MEDICINE_ENROLLMENTS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.MEDICINE_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.PAIN_RECORDS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.USERS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.records.MedicineRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public class MedicineRepositoryImpl implements IMedicineRepository {
  private final DSLContext create;
  private static final MEDICINE_TABLE M = MEDICINE_TABLE.MEDICINE.as("M");
  private static final MEDICINE_ENROLLMENTS_TABLE ME =
      MEDICINE_ENROLLMENTS_TABLE.MEDICINE_ENROLLMENTS.as("ME");
  private static final PAIN_RECORDS_TABLE P = PAIN_RECORDS_TABLE.PAIN_RECORDS.as("P");
  private static final USERS_TABLE U = USERS_TABLE.USERS.as("U");

  @Override
  public List<Medicine> findAllByPainRecordId(String painRecordId) {
    List<Record> selected = new ArrayList<>();
    try {
      selected =
          create
              .select(M.asterisk(), ME.asterisk())
              .from(M)
              .join(ME)
              .on(ME.MEDICINE_ID.eq(M.MEDICINE_ID))
              .join(P)
              .on(P.PAIN_RECORD_ID.eq(ME.PAIN_RECORD_ID))
              .where(P.PAIN_RECORD_ID.eq(painRecordId))
              .and(M.STATUS.eq(Status.ALIVE.name()))
              .orderBy(ME.MEDICINE_SEQ.asc())
              .fetch();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return selected
        .stream()
        .map(
            record -> {
              return Medicine.builder()
                  .medicineId(record.get(M.MEDICINE_ID))
                  .userId(record.get(M.USER_ID))
                  .medicineSeq(record.get(ME.MEDICINE_SEQ))
                  .medicineName(record.get(M.MEDICINE_NAME))
                  .status(record.get(M.STATUS))
                  .createdAt(record.get(M.CREATED_AT))
                  .updatedAt(record.get(M.UPDATED_AT))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void save(PainRecord painRecord) {
    Integer resultCount = -1;
    try {
      resultCount =
          painRecord
              .getMedicineList()
              .stream()
              .mapToInt(
                  medicine -> {
                    Integer medicineCount =
                        create
                            .insertInto(M)
                            .set(M.MEDICINE_ID, medicine.getMedicineId())
                            .set(M.USER_ID, medicine.getUserId())
                            .set(M.MEDICINE_NAME, medicine.getMedicineName())
                            .set(M.MEDICINE_MEMO, medicine.getMedicineMemo())
                            .set(M.STATUS, medicine.getStatus())
                            .set(M.CREATED_AT, medicine.getCreatedAt())
                            .set(M.UPDATED_AT, medicine.getUpdatedAt())
                            .onDuplicateKeyUpdate()
                            .set(M.USER_ID, medicine.getUserId())
                            .set(M.MEDICINE_NAME, medicine.getMedicineName())
                            .set(M.MEDICINE_MEMO, medicine.getMedicineMemo())
                            .set(M.STATUS, medicine.getStatus())
                            .set(M.UPDATED_AT, medicine.getUpdatedAt())
                            .execute();

                    Integer enrollmentCount =
                        create
                            .insertInto(ME)
                            .set(ME.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(ME.MEDICINE_ID, medicine.getMedicineId())
                            .set(ME.MEDICINE_SEQ, medicine.getMedicineSeq())
                            .onDuplicateKeyUpdate()
                            .set(ME.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(ME.MEDICINE_ID, medicine.getMedicineId())
                            .set(ME.MEDICINE_SEQ, medicine.getMedicineSeq())
                            .execute();

                    return medicineCount + enrollmentCount;
                  })
              .sum();
      log.info("Medicine save count : {}", resultCount);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  @Transactional
  public void delete(String painRecordId) {
    try {
      int result = create.delete(ME).where(ME.PAIN_RECORD_ID.eq(painRecordId)).execute();
      log.info("Delete medicine enrollments : count = {}, painRecordId = {}", result, painRecordId);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public List<Medicine> findAllByUserId(String userId) {
    List<Record> selected = new ArrayList<>();
    try {
      selected =
          create
              .select(M.asterisk())
              .from(U)
              .join(M)
              .on(U.USER_ID.eq(M.USER_ID))
              .where(M.USER_ID.eq(userId))
              .and(M.STATUS.eq(Status.ALIVE.toString()))
              .fetch();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return selected
        .stream()
        .map(
            record -> {
              return Medicine.builder()
                  .medicineId(record.get(M.MEDICINE_ID))
                  .userId(record.get(M.USER_ID))
                  .medicineName(record.get(M.MEDICINE_NAME))
                  .status(record.get(M.STATUS))
                  .createdAt(record.get(M.CREATED_AT))
                  .updatedAt(record.get(M.UPDATED_AT))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public void save(Medicine medicine) {
    Integer result = -1;
    try {
      result =
          create
              .insertInto(M)
              .set(M.MEDICINE_ID, medicine.getMedicineId())
              .set(M.USER_ID, medicine.getUserId())
              .set(M.MEDICINE_NAME, medicine.getMedicineName())
              .set(M.STATUS, medicine.getStatus())
              .set(M.CREATED_AT, medicine.getCreatedAt())
              .set(M.UPDATED_AT, medicine.getUpdatedAt())
              .onDuplicateKeyUpdate()
              .set(M.MEDICINE_NAME, medicine.getMedicineName())
              .set(M.STATUS, medicine.getStatus())
              .set(M.UPDATED_AT, medicine.getUpdatedAt())
              .execute();

      log.info("Save medicine : count = {}, medicine = {}", result, medicine.toString());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public Optional<Medicine> findByMedicineId(String medicineId) {
    Optional<MedicineRecord> selected = Optional.empty();
    try {
      selected = create.selectFrom(M).where(M.MEDICINE_ID.eq(medicineId)).fetchOptional();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return selected.map(
        v ->
            Medicine.builder()
                .medicineId(v.getMedicineId())
                .medicineName(v.getMedicineName())
                .medicineMemo(v.getMedicineMemo())
                .status(v.getStatus())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .build());
  }

  @Override
  @Transactional
  public void deleteByMedicineId(String medicineId) {
    try {
      int result =
          create
              .update(M)
              .set(M.STATUS, Status.DELETED.toString())
              .where(M.MEDICINE_ID.eq(medicineId))
              .execute();
      log.info("Delete medicine : count = {}, painRecordId = {}", result, medicineId);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
