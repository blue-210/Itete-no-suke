package app.itetenosuke.api.domain.painrecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import app.itetenosuke.api.domain.bodypart.BodyPart;
import app.itetenosuke.api.domain.image.Image;
import app.itetenosuke.api.domain.medicine.Medicine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PainRecord {
  private final String painRecordId;
  private final String userId;
  // 痛みレベル
  @NotNull
  @Range(min = 0, max = 3)
  private final Integer painLevel;

  // お薬
  @Valid private List<Medicine> medicineList;

  // 部位
  @Valid private List<BodyPart> bodyPartsList;

  // 部位画像
  private List<Image> imageList;

  // メモ
  @Length(min = 0, max = 250)
  private final String memo;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private final LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private final LocalDateTime updatedAt;
}
