package app.itetenosuke.api.domain.painrecord;

import java.util.List;
import java.util.Optional;

public interface IPainRecordRepository {
  public Optional<PainRecord> findById(String painRecordId);

  public void save(PainRecord painRecord);

  public List<PainRecord> findAllByUserId(String userId);

  public void delete(String painRecordId);
}
