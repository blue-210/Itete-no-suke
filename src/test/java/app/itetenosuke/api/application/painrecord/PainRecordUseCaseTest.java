package app.itetenosuke.api.application.painrecord;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.itetenosuke.api.domain.bodypart.BodyPart;
import app.itetenosuke.api.domain.medicine.Medicine;
import app.itetenosuke.api.domain.painrecord.PainLevel;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.presentation.controller.painrecord.PainRecordReqBody;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
@Slf4j
class PainRecordUseCaseTest {
  @Autowired private PainRecordUseCase painRecordUseCase;

  @Test
  @DisplayName("痛み記録を1件取得できる")
  @DatabaseSetup("/painrecord/setup_get_a_record.xml")
  public void testGetPainRecord() {
    PainRecordDto result = painRecordUseCase.getPainRecord("123456789012345678901234567890123456");
    assertAll(
        "result",
        () -> assertThat(result.getPainRecordId(), is("123456789012345678901234567890123456")),
        () -> assertThat(result.getPainLevel(), is(3)),
        () -> assertThat(result.getMedicineList().size(), is(2)),
        () -> assertThat(result.getMemo(), is("test")));
  }

  @Test
  @DisplayName("痛み記録を1件更新できる")
  @DatabaseSetup(value = "/painrecord/setup_update_a_record.xml")
  @ExpectedDatabase(
      value = "/painrecord/expected_update_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testUpdatePainRecord() {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setPainLevel(PainLevel.VERY_SEVERE_PAIN.getCode());
    req.setMemo("update test");
    req.setCreatedAt(LocalDateTime.now());
    req.setUpdatedAt(LocalDateTime.now());

    Medicine medicine1 =
        Medicine.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .medicineSeq(1)
            .medicineName("update medicine1")
            .status(Status.ALIVE.name())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Medicine medicine2 =
        Medicine.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm2")
            .medicineSeq(2)
            .medicineName("update medicine2")
            .status(Status.ALIVE.name())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Medicine medicine3 =
        Medicine.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm3")
            .medicineSeq(3)
            .medicineName("update insert medicine3")
            // STATUSのenum作成しておく
            .status(Status.ALIVE.name())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    List<Medicine> medicineList = new ArrayList<>();
    medicineList.add(medicine1);
    medicineList.add(medicine2);
    medicineList.add(medicine3);
    req.setMedicineList(medicineList);

    // TODO DBより取得する形に修正する
    BodyPart bodyPart1 =
        BodyPart.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .bodyPartName("部位更新1")
            .bodyPartSeq(1)
            .status("ALIVE")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    BodyPart bodyPart2 =
        BodyPart.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb2")
            .bodyPartName("部位更新(新規追加)")
            .bodyPartSeq(2)
            .status(Status.ALIVE.name())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    List<BodyPart> bodyPartsList = new ArrayList<>();
    bodyPartsList.add(bodyPart1);
    bodyPartsList.add(bodyPart2);
    req.setBodyPartsList(bodyPartsList);

    assertThat(painRecordUseCase.updatePainRecord(req), is(true));
  }

  @Test
  @DisplayName("痛み記録を1件登録できる")
  @DatabaseSetup(value = "/painrecord/setup_create_a_record.xml")
  @ExpectedDatabase(
      value = "/painrecord/expected_create_a_record.xml",
      table = "pain_records",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testCreatePainRecord() {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setPainLevel(PainLevel.MODERATE.getCode());
    req.setMemo("create test");
    req.setCreatedAt(LocalDateTime.now());
    req.setUpdatedAt(LocalDateTime.now());
    assertThat(painRecordUseCase.createPainRecord(req), is(true));
  }
}
