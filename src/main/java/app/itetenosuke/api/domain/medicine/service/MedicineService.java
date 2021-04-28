package app.itetenosuke.domain.medicine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.medicine.repository.MedicineDao;

@Transactional(rollbackFor = Exception.class)
@Service
public class MedicineService {
  @Autowired
  @Qualifier("MedicineDaoNamedJdbcImpl")
  MedicineDao medicineDao;

  public List<Medicine> getMedicineList(long userId) {
    return medicineDao.getMedicineList(userId);
  }

  public boolean addMedicine(Medicine medicine) {
    return medicineDao.addMedicine(medicine);
  }

  public boolean deleteMedicine(Medicine medicine) {
    return medicineDao.deleteMedicine(medicine);
  }

  public boolean editMedicine(Medicine medicine) {
    return medicineDao.editMedicine(medicine);
  }

}
