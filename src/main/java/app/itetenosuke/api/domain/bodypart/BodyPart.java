package app.itetenosuke.api.domain.bodypart;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class BodyPart {
  @Default private String bodyPartId = UUID.randomUUID().toString();
  private String bodyPartName;
  private Integer bodyPartSeq;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
