package app.itetenosuke.repository.medicine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.medicine.repository.MedicineDao;

// @ExtendWith(SpringExtension.class)
@SpringBootTest
class MedicineDaoTest {
  @Autowired
  @Qualifier("MedicineDaoNamedJdbcImpl")
  MedicineDao medicineDao;

  @Test
  @Sql("/medicine/testdata_medicinelist.sql")
  @Transactional
  @DisplayName("薬情報一覧を取得するテスト")
  void testGetMedicineList() {
    List<Medicine> result = medicineDao.getMedicineList(Long.valueOf(1));

    assertEquals(3, result.size());
  }

  @Test
  @Sql("/medicine/testdata_no_medicine.sql")
  @Transactional
  @DisplayName("薬情報を1件登録してtrueが返ってくること")
  void testAddMedicineReturnTrue() {
    Medicine medicine = new Medicine();
    medicine.setUserId(Long.valueOf(1));
    medicine.setMedicineName("薬1件登録テスト");
    boolean result = medicineDao.addMedicine(medicine);

    assertEquals(true, result);
  }

  @Test
  @Sql("/medicine/testdata_no_medicine.sql")
  @Transactional
  @DisplayName("薬情報を1件登録してDBに1件登録されていること")
  void testAddMedicineDbhasOneRecord() {
    Medicine medicine = new Medicine();
    medicine.setUserId(Long.valueOf(1));
    medicine.setMedicineName("薬1件登録テスト");

    medicineDao.addMedicine(medicine);

    List<Medicine> result = medicineDao.getMedicineList(Long.valueOf(1));
    assertEquals(1, result.size());
  }

  @Test
  @Sql("/medicine/testdata_medicinelist.sql")
  @Transactional
  @DisplayName("薬情報を1件削除できること")
  void testDeleteMedicine() {
    List<Medicine> list = medicineDao.getMedicineList(Long.valueOf(1));
    list.get(0).setUserId(Long.valueOf(1));

    boolean result = medicineDao.deleteMedicine(list.get(0));

    assertEquals(true, result);
  }

}
