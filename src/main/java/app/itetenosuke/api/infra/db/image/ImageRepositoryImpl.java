package app.itetenosuke.api.infra.db.image;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.image.IImageRepository;
import app.itetenosuke.api.domain.image.Image;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.infra.db.jooq.generated.tables.IMAGES_ENROLLMENTS_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.IMAGES_TABLE;
import app.itetenosuke.infra.db.jooq.generated.tables.PAIN_RECORDS_TABLE;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository(value = "ImageRepositoryImpl")
@AllArgsConstructor
@Slf4j
public class ImageRepositoryImpl implements IImageRepository {
  private final DSLContext create;
  private static final IMAGES_TABLE I = IMAGES_TABLE.IMAGES.as("I");
  private static final IMAGES_ENROLLMENTS_TABLE IE =
      IMAGES_ENROLLMENTS_TABLE.IMAGES_ENROLLMENTS.as("IE");
  private static final PAIN_RECORDS_TABLE P = PAIN_RECORDS_TABLE.PAIN_RECORDS.as("P");

  @Override
  @Transactional
  public List<Image> save(PainRecord painRecord) {
    Integer resultCount = -1;
    try {
      resultCount =
          painRecord
              .getImageList()
              .stream()
              .mapToInt(
                  image -> {
                    Integer imagesCount =
                        create
                            .insertInto(I)
                            .set(I.IMAGE_ID, image.getImageId())
                            .set(I.USER_ID, image.getUserId())
                            .set(I.IMAGE_PATH, image.getImagePath())
                            .set(I.STATUS, image.getStatus())
                            .set(I.CREATED_AT, image.getCreatedAt())
                            .set(I.UPDATED_AT, image.getUpdatedAt())
                            .onDuplicateKeyUpdate()
                            .set(I.IMAGE_PATH, image.getImagePath())
                            .set(I.STATUS, image.getStatus())
                            .set(I.UPDATED_AT, image.getUpdatedAt())
                            .execute();

                    Integer enrollmentCount =
                        create
                            .insertInto(IE)
                            .set(IE.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(IE.IMAGE_ID, image.getImageId())
                            .set(IE.IMAGE_SEQ, image.getImageSeq())
                            .onDuplicateKeyUpdate()
                            .set(IE.PAIN_RECORD_ID, painRecord.getPainRecordId())
                            .set(IE.IMAGE_ID, image.getImageId())
                            .set(IE.IMAGE_SEQ, image.getImageSeq())
                            .execute();

                    return imagesCount + enrollmentCount;
                  })
              .sum();
      log.info("Image save count : {}", resultCount);
    } catch (Exception e) {
      log.error("Image save info : {}", painRecord.toString());
      log.error(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public List<Image> findAllByPainRecordId(String painRecordID) {
    List<Record> selected = new ArrayList<>();
    try {
      selected =
          create
              .select(I.asterisk(), IE.asterisk())
              .from(I)
              .join(IE)
              .on(I.IMAGE_ID.eq(IE.IMAGE_ID))
              .join(P)
              .on(P.PAIN_RECORD_ID.eq(IE.PAIN_RECORD_ID))
              .where(P.PAIN_RECORD_ID.eq(painRecordID))
              .orderBy(IE.IMAGE_SEQ)
              .fetch();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return selected
        .stream()
        .map(
            record -> {
              return Image.builder()
                  .imageId(record.get(I.IMAGE_ID))
                  .userId(record.get(I.USER_ID))
                  .imageSeq(record.get(IE.IMAGE_SEQ))
                  .imagePath(record.get(I.IMAGE_PATH))
                  .status(record.get(I.STATUS))
                  .createdAt(record.get(I.CREATED_AT))
                  .updatedAt(record.get(I.UPDATED_AT))
                  .build();
            })
        .collect(Collectors.toList());
  }
}
