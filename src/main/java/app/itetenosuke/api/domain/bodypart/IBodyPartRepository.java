package app.itetenosuke.api.domain.bodypart;

import app.itetenosuke.api.domain.painrecord.PainRecord;

public interface IBodyPartRepository {
  public void save(PainRecord painRecord);
}
