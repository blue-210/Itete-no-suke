package app.itetenosuke.domain.painrecord;

import java.util.UUID;

import lombok.Value;

@Value
public class PainRecordId {
  private String value;

  public PainRecordId() {
    this.value = UUID.randomUUID().toString();
  }
}
