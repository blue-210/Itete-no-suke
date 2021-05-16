package app.itetenosuke.api.domain.image;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import app.itetenosuke.api.domain.shared.Status;
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
public class Image {
  @Default private String imageId = UUID.randomUUID().toString();
  private String userId;
  private String imagePath;
  private Integer imageSeq;
  private MultipartFile file;
  @Default private String status = Status.ALIVE.toString();
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
