package app.itetenosuke.api.presentation.controller.shared;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import app.itetenosuke.api.application.painrecord.PainRecordDto;
import app.itetenosuke.api.domain.bodypart.BodyPart;
import app.itetenosuke.api.domain.image.Image;
import app.itetenosuke.api.domain.medicine.Medicine;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PainRecordResBody {
  // Presentation Layerからのみ利用
  // static ファクトリメソッドでやってみる
  public static PainRecordResBody of(PainRecordDto painRecordDto) {
    return new PainRecordResBody(
        painRecordDto.getPainRecordId(),
        painRecordDto.getUserId(),
        painRecordDto.getPainLevel(),
        painRecordDto.getMemo(),
        painRecordDto.getMedicineList(),
        painRecordDto.getBodyPartList(),
        painRecordDto.getImageList(),
        painRecordDto.getCreatedAt(),
        painRecordDto.getUpdatedAt());
  }

  private String painRecordId;
  private String userId;
  private Integer painLevel;
  private String memo;
  private List<Medicine> medicineList;
  private List<BodyPart> bodyPartList;
  private List<Image> imageList;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime updatedAt;
}
