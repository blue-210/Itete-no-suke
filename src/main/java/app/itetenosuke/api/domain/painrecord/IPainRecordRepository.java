package app.itetenosuke.domain.painrecord;

import java.util.Optional;

public interface IPainRecordRepository {
  public Optional<PainRecord> findById(String painRecordId);

  public boolean createPainRecord(PainRecord painRecord);

  public boolean updatePainRecord(PainRecord painRecord);
}
