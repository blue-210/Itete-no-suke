package app.itetenosuke.api.presentation.controller.shared;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import app.itetenosuke.api.application.bodypart.BodyPartDto;
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
public class BodyPartResBody {
  public static BodyPartResBody of(BodyPartDto bodyPartDto) {
    return BodyPartResBody.builder()
        .bodyPartId(bodyPartDto.getBodyPartId())
        .userId(bodyPartDto.getUserId())
        .bodyPartName(bodyPartDto.getBodyPartName())
        .status(bodyPartDto.getStatus())
        .createdAt(bodyPartDto.getCreatedAt())
        .updatedAt(bodyPartDto.getUpdatedAt())
        .build();
  }

  private String bodyPartId;
  private Integer bodyPartSeq;
  private String userId;
  private String bodyPartName;
  private String status;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime updatedAt;
}
