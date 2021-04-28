package app.itetenosuke.api.domain.painrecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import app.itetenosuke.api.domain.medicine.Medicine;
import lombok.Getter;

// @Data
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
  // @Valid private List<BodyParts> bodyPartsList;

  // 部位画像
  // private List<Image> imageList;

  // メモ
  @Length(min = 0, max = 250)
  private final String memo;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private final LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private final LocalDateTime updatedAt;

  public static class Builder {
    private String painRecordId;
    private String userId;
    private Integer painLevel = PainLevel.MODERATE.getCode();
    private List<Medicine> medicineList;
    private String memo = "";
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Builder setPainRecordId(String painRecordId) {
      this.painRecordId = painRecordId;
      return this;
    }

    public Builder setUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public Builder setPainLevel(Integer painLevel) {
      this.painLevel = painLevel;
      return this;
    }

    public Builder setMedicineList(List<Medicine> medicineList) {
      this.medicineList = medicineList;
      return this;
    }

    public Builder setMemo(String memo) {
      this.memo = memo;
      return this;
    }

    public Builder setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public PainRecord build() {
      return new PainRecord(this);
    }
  }

  private PainRecord(Builder builder) {
    painRecordId = builder.painRecordId;
    userId = builder.userId;
    painLevel = builder.painLevel;
    medicineList = builder.medicineList;
    memo = builder.memo;
    createdAt = builder.createdAt;
    updatedAt = builder.updatedAt;
  }
}
