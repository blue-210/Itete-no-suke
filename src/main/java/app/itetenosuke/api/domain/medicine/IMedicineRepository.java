package app.itetenosuke.api.domain.medicine;

import java.util.List;

import app.itetenosuke.api.domain.painrecord.PainRecord;

// TODO 戻り値をvoidにするかも？→コレクション型のリポジトリを意識
public interface IMedicineRepository {
  public List<Medicine> findAllByPainRecordId(String painRecordId);

  public boolean createMedicineRecords(PainRecord painRecord);

  public boolean updateMedicineRecords(PainRecord painRecord);

  public boolean exists(Medicine medicine, String painRecordId);
}
