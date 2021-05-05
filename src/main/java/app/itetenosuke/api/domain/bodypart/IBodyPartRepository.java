package app.itetenosuke.api.domain.bodypart;

import java.util.List;
import java.util.Optional;

import app.itetenosuke.api.domain.painrecord.PainRecord;

public interface IBodyPartRepository {
  public void save(PainRecord painRecord);

  public List<BodyPart> findAllByPainRecordId(String painRecordID);

  public void delete(String painRecordId);

  public List<BodyPart> findAllByUserId(String userId);

  public Optional<BodyPart> findByBodyPartId(String bodyPartId);

  public void save(BodyPart bodyPart);

  public void deleteByBodyPartId(String bodyPartId);
}
