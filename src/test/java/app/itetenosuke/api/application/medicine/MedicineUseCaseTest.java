package app.itetenosuke.api.application.medicine;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.presentation.controller.shared.MedicineReqBody;
import shared.TestDatetimeHelper;

@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  WithSecurityContextTestExecutionListener.class
})
@Transactional
class MedicineUseCaseTest {
  @Autowired private MedicineUseCase medicineUseCase;

  @Test
  @DisplayName("お薬一覧を取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/medicine/setup_get_medicines.xml")
  void testGetMedicineList() {
    List<MedicineDto> expected = new ArrayList<>();
    MedicineDto medicine1 =
        MedicineDto.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .medicineName("薬1")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();
    MedicineDto medicine2 =
        MedicineDto.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm2")
            .medicineName("薬2")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();
    expected.add(medicine1);
    expected.add(medicine2);

    List<MedicineDto> actual =
        medicineUseCase.getMedicineList("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1");

    assertThat(actual.size(), is(expected.size()));
    assertIterableEquals(expected, actual);
  }

  @Test
  @DisplayName("お薬を登録できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/medicine/setup_create_a_medicine.xml")
  @ExpectedDatabase(
      value = "/application/medicine/expected_create_a_medicine.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  void testCreateMedicineList() {
    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .medicineName("登録薬1")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    medicineUseCase.createMedicine(medicine1);
  }

  @Test
  @DisplayName("お薬を更新できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/medicine/setup_update_a_medicine.xml")
  @ExpectedDatabase(
      value = "/application/medicine/expected_update_a_medicine.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  void testUpdateMedicine() {
    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm2")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .medicineName("更新 薬2")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    medicineUseCase.updateMedicine(medicine1);
  }

  @Test
  @DisplayName("お薬を削除できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/medicine/setup_delete_a_medicine.xml")
  @ExpectedDatabase(
      value = "/application/medicine/expected_delete_a_medicine.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  void testDeleteMedicine() {
    medicineUseCase.deleteMedicine("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm3");
  }
}
