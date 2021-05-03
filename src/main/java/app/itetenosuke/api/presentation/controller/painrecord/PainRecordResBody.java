package app.itetenosuke.api.presentation.controller.painrecord;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import app.itetenosuke.api.application.painrecord.PainRecordDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PainRecordResBody {
  // Presentation Layerからのみ利用
  // static ファクトリメソッドでやってみる
  static PainRecordResBody of(PainRecordDto painRecordDto) {
    return new PainRecordResBody(
        painRecordDto.getPainRecordId(),
        painRecordDto.getUserId(),
        painRecordDto.getPainLevel(),
        painRecordDto.getMemo(),
        painRecordDto.getCreatedAt(),
        painRecordDto.getUpdatedAt());
  }

  private String painRecordId;
  private String userId;
  private Integer painLevel;
  // TODO 薬、部位、画像のエンティティ追加
  private String memo;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy年M月d日H時m分")
  private LocalDateTime updatedAt;
}
