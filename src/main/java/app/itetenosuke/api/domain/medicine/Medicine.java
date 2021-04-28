package app.itetenosuke.api.domain.medicine;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Medicine {
  @Default private final String medicineId = UUID.randomUUID().toString();
  private final String userId;

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
