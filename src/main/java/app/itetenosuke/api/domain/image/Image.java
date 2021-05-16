package app.itetenosuke.api.domain.image;

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
public class Image {
  @Default private String imageId = UUID.randomUUID().toString();
  private String imagePath;
  private Integer imageSeq;
}
