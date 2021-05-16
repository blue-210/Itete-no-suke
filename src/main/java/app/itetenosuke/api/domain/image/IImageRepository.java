package app.itetenosuke.api.domain.image;

import java.util.List;

import app.itetenosuke.api.domain.painrecord.PainRecord;

public interface IImageRepository {
  public List<Image> save(PainRecord painRecord);

  public List<Image> findAllByPainRecordId(String painRecordID);
}
