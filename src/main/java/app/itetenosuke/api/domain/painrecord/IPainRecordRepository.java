package app.itetenosuke.api.domain.painrecord;

import java.util.Optional;

public interface IPainRecordRepository {
  public Optional<PainRecord> findById(String painRecordId);

  public void save(PainRecord painRecord);
}
