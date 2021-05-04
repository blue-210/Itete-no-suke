package app.itetenosuke.api.presentation.controller.shared;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import app.itetenosuke.api.application.medicine.MedicineDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Builder
public class MedicineResBody {

  public static MedicineResBody of(MedicineDto medicineDto) {
    return MedicineResBody.builder()
        .medicineId(medicineDto.getMedicineId())
        .medicineName(medicineDto.getMedicineName())
        .medicineMemo(medicineDto.getMedicineMemo())
        .status(medicineDto.getStatus())
        .createdAt(medicineDto.getCreatedAt())
        .updatedAt(medicineDto.getUpdatedAt())
        .build();
  }

  private String medicineId;
  private Integer medicineSeq;
  private String medicineName;
  private String medicineMemo;
  private String status;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime updatedAt;
}
