package app.itetenosuke.api.domain.medicine;

import java.util.List;
import java.util.Optional;

import app.itetenosuke.api.domain.painrecord.PainRecord;

// TODO 戻り値をvoidにするかも？→コレクション型のリポジトリを意識
public interface IMedicineRepository {
  public List<Medicine> findAllByPainRecordId(String painRecordId);

  public void save(PainRecord painRecord);

  public void save(Medicine medicine);

  public void delete(String painRecordId);

  public List<Medicine> findAllByUserId(String userId);

  public Optional<Medicine> getMedicineByMedicineId(String medicineId);
}
