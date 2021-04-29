package app.itetenosuke.api.infra.db.bodypart;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.bodypart.IBodyPartRepository;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.BODY_PARTS_ENROLLMENTS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.BODY_PARTS_TABLE;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public class BodyPartRepositoryImpl implements IBodyPartRepository {
  private final DSLContext create;
  private static final BODY_PARTS_TABLE B = BODY_PARTS_TABLE.BODY_PARTS.as("B");
  private static final BODY_PARTS_ENROLLMENTS_TABLE BE =
      BODY_PARTS_ENROLLMENTS_TABLE.BODY_PARTS_ENROLLMENTS.as("BE");

  @Override
  @Transactional
  public void save(PainRecord painRecord) {
    Integer resultCount = -1;
    try {
      resultCount =
          painRecord
              .getBodyPartsList()
              .stream()
              .mapToInt(
                  bodyPart -> {
                    Integer bodyPartsCount =
                        create
                            .insertInto(B)
                            .set(B.BODY_PART_ID, bodyPart.getBodyPartId())
                            .set(B.BODY_PART_NAME, bodyPart.getBodyPartName())
                            .set(B.STATUS, bodyPart.getStatus())
                            .set(B.CREATED_AT, bodyPart.getCreatedAt())
                            .set(B.UPDATED_AT, bodyPart.getUpdatedAt())
                            .onDuplicateKeyUpdate()
                            .set(B.BODY_PART_NAME, bodyPart.getBodyPartName())
                            .set(B.STATUS, bodyPart.getStatus())
                            .set(B.UPDATED_AT, bodyPart.getUpdatedAt())
                            .execute();

                    Integer enrollmentCount =
                        create
                            .insertInto(BE)
                            .set(BE.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(BE.BODY_PART_ID, bodyPart.getBodyPartId())
                            .set(BE.BODY_PART_SEQ, bodyPart.getBodyPartSeq())
                            .onDuplicateKeyUpdate()
                            .set(BE.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(BE.BODY_PART_ID, bodyPart.getBodyPartId())
                            .set(BE.BODY_PART_SEQ, bodyPart.getBodyPartSeq())
                            .execute();
                    return bodyPartsCount + enrollmentCount;
                  })
              .sum();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
