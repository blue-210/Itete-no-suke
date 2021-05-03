package app.itetenosuke.api.infra.db.painrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.jooq.DSLContext;
import org.jooq.Record;
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
              .userId(v.getUserId())
              .painLevel(v.getPainLevel())
              .memo(v.getMemo())
              .createdAt(v.getCreatedAt())
              .updatedAt(v.getUpdatedAt())
              .build();
        });
  }

  @Override
  @Transactional
  public void save(PainRecord painRecord) {
    Integer resultCount = -1;
    try {
      resultCount =
          create
              .insertInto(P)
              .set(P.PAIN_RECORD_ID, painRecord.getPainRecordId())
              .set(P.USER_ID, painRecord.getUserId())
              .set(P.PAIN_LEVEL, painRecord.getPainLevel())
              .set(P.MEMO, painRecord.getMemo())
              .set(P.CREATED_AT, painRecord.getCreatedAt())
              .set(P.UPDATED_AT, painRecord.getUpdatedAt())
              .onDuplicateKeyUpdate()
              .set(P.PAIN_LEVEL, painRecord.getPainLevel())
              .set(P.USER_ID, painRecord.getUserId())
              .set(P.MEMO, painRecord.getMemo())
              .set(P.UPDATED_AT, painRecord.getUpdatedAt())
              .execute();
      log.info("Painrecord save count : {}", resultCount);
    } catch (Exception e) {
      log.error("PainRecord save info : {}", painRecord.toString());
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public List<PainRecord> findAllByUserId(String userId) {
    List<Record> resultList = new ArrayList<>();
    try {
      resultList =
          create
              .select(P.asterisk())
              .from(P)
              .where(P.USER_ID.eq(userId))
              .orderBy(P.CREATED_AT.desc())
              .fetch();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return resultList
        .stream()
        .map(
            record -> {
              return PainRecord.builder()
                  .userId(record.get(P.USER_ID))
                  .painRecordId(record.get(P.PAIN_RECORD_ID))
                  .painLevel(record.get(P.PAIN_LEVEL))
                  .memo(record.get(P.MEMO))
                  .createdAt(record.get(P.CREATED_AT))
                  .updatedAt(record.get(P.UPDATED_AT))
                  .build();
            })
        .collect(Collectors.toList());
  }
}
