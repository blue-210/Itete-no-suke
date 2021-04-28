package app.itetenosuke.application.painrecord;

import java.time.LocalDateTime;

import app.itetenosuke.domain.painrecord.PainRecord;
import lombok.Getter;

@Getter
public class PainRecordDto {
  public PainRecordDto(PainRecord painRecord) {
    this.painRecordId = painRecord.getPainRecordId();
    this.painLevel = painRecord.getPainLevel();
    this.memo = painRecord.getMemo();
    this.createdAt = painRecord.getCreatedAt();
    this.updatedAt = painRecord.getUpdatedAt();
  }

  private String painRecordId;
  private String userId;
  private Integer painLevel;
  // TODO 薬、部位、画像のエンティティ追加
  private String memo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
