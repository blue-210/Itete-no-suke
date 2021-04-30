package app.itetenosuke.api.application.painrecord;

import java.time.LocalDateTime;
import java.util.List;

import app.itetenosuke.api.domain.bodypart.BodyPart;
import app.itetenosuke.api.domain.medicine.Medicine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PainRecordDto {

  private String painRecordId;
  private String userId;
  private Integer painLevel;
  // TODO 薬、部位、画像のエンティティ追加
  private List<Medicine> medicineList;
  private List<BodyPart> bodyPartList;
  private String memo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
