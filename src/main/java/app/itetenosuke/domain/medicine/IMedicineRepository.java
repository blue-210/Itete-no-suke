package app.itetenosuke.domain.medicine;

import app.itetenosuke.domain.painrecord.PainRecord;

public interface IMedicineRepository {
  public boolean createMedicineRecords(PainRecord painRecord);

  public boolean updateMedicineRecords(PainRecord painRecord);

  public boolean exists(Medicine medicine, String painRecordId);
}
