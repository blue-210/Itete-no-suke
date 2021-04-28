package app.itetenosuke.domain.medicine;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Medicine {
  private String medicineId;
  private String userId;

  @Min(1)
  @Max(5)
  @NotNull
  private Integer medicineSeq;

  @Length(min = 1, max = 100)
  @NotNull
  private String medicineName;

  private String medicineMemo;

  private String status;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime updatedAt;
}
