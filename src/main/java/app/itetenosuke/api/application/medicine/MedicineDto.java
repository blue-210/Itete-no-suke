package app.itetenosuke.api.application.medicine;

import java.time.LocalDateTime;

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
public class MedicineDto {
  private String medicineId;
  private Integer medicineSeq;
  private String userId;
  private String medicineName;
  private String medicineMemo;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
