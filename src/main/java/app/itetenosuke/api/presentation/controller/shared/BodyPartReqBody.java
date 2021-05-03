package app.itetenosuke.api.presentation.controller.shared;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class BodyPartReqBody {
  private String userId;
  private String bodyPartId;

  @Min(1)
  @Max(5)
  @NotNull
  private Integer bodyPartSeq;

  @Length(min = 1, max = 20)
  @NotNull
  private String bodyPartName;

  private String status;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime updatedAt;
}
