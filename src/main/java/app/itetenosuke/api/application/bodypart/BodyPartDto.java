package app.itetenosuke.api.application.bodypart;

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
public class BodyPartDto {
  private String bodyPartId;
  private Integer bodyPartSeq;
  private String userId;
  private String bodyPartName;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
