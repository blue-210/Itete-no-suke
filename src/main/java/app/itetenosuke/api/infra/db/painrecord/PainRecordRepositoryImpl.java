package app.itetenosuke.api.infra.db.painrecord;

import java.util.Optional;

import javax.transaction.Transactional;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.TooManyRowsException;
import org.springframework.stereotype.Repository;

import app.itetenosuke.api.domain.painrecord.IPainRecordRepository;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.PAIN_RECORDS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.records.PainRecordsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public class PainRecordRepositoryImpl implements IPainRecordRepository {

  private final DSLContext create;

  private static final PAIN_RECORDS_TABLE P = PAIN_RECORDS_TABLE.PAIN_RECORDS.as("P");

  @Override
  public Optional<PainRecord> findById(String painRecordId) {
    Optional<PainRecordsRecord> selected = Optional.empty();
    try {
      selected = create.selectFrom(P).where(P.PAIN_RECORD_ID.eq(painRecordId)).fetchOptional();
      // TODO ただエラーをログ出力するならExceptionでまとめてもいいかも？
    } catch (TooManyRowsException tmre) {
      log.warn(tmre.getMessage(), tmre);
      throw tmre;
    } catch (DataAccessException dae) {
      log.error(dae.getMessage(), dae);
      throw dae;
    }
    return selected.map(
        v -> {
          return PainRecord.builder()
              .painRecordId(v.getPainRecordId())
              .painLevel(v.getPainLevel())
              .memo(v.getMemo())
              .createdAt(v.getCreatedAt())
              .updatedAt(v.getUpdatedAt())
              .build();
        });
  }

  @Override
  @Transactional
  public boolean updatePainRecord(PainRecord painRecord) {
    Integer updateCount = -1;
    try {
      updateCount =
          create
              .update(P)
              .set(P.PAIN_LEVEL, painRecord.getPainLevel())
              .set(P.MEMO, painRecord.getMemo())
              .set(P.UPDATED_AT, painRecord.getUpdatedAt())
              .where(P.PAIN_RECORD_ID.eq(painRecord.getPainRecordId()))
              .execute();
      log.info("update a pain record : {}", updateCount);
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
    return updateCount > 0 ? true : false;
  }

  @Override
  @Transactional
  public boolean createPainRecord(PainRecord painRecord) {
    Integer createCount = -1;
    try {
      createCount =
          create
              .insertInto(P)
              .set(P.PAIN_RECORD_ID, painRecord.getPainRecordId())
              .set(P.PAIN_LEVEL, painRecord.getPainLevel())
              .set(P.MEMO, painRecord.getMemo())
              .set(P.CREATED_AT, painRecord.getCreatedAt())
              .set(P.UPDATED_AT, painRecord.getUpdatedAt())
              .execute();
      log.info("create a pain record : {}", createCount);
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
    return createCount > 0 ? true : false;
  }
}
