package app.itetenosuke.api.application.painrecord;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.itetenosuke.api.domain.painrecord.PainLevel;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.presentation.controller.shared.BodyPartReqBody;
import app.itetenosuke.api.presentation.controller.shared.MedicineReqBody;
import app.itetenosuke.api.presentation.controller.shared.PainRecordReqBody;
import lombok.extern.slf4j.Slf4j;
import shared.TestDatetimeHelper;

@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  WithSecurityContextTestExecutionListener.class
})
@Slf4j
class PainRecordUseCaseTest {
  @Autowired private PainRecordUseCase painRecordUseCase;

  @Test
  @DisplayName("痛み記録を1件取得できる")
  @DatabaseSetup("/application/painrecord/setup_get_a_record.xml")
  public void testGetPainRecord() {
    PainRecordDto result = painRecordUseCase.getPainRecord("123456789012345678901234567890123456");
    assertAll(
        "result",
        () -> assertThat(result.getPainRecordId(), is("123456789012345678901234567890123456")),
        () -> assertThat(result.getPainLevel(), is(3)),
        () -> assertThat(result.getMedicineList().size(), is(2)),
        () -> assertThat(result.getBodyPartList().size(), is(1)),
        () -> assertThat(result.getMemo(), is("test")));
  }

  @Test
  @DisplayName("痛み記録を1件更新できる")
  @DatabaseSetup(value = "/application/painrecord/setup_update_a_record.xml")
  @ExpectedDatabase(
      value = "/application/painrecord/expected_update_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testUpdatePainRecord() {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setPainLevel(PainLevel.VERY_SEVERE_PAIN.getCode());
    req.setMemo("update test");
    req.setCreatedAt(TestDatetimeHelper.getTestDatetime());
    req.setUpdatedAt(TestDatetimeHelper.getTestDatetime());

    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .medicineSeq(1)
            .medicineName("update medicine1")
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MedicineReqBody medicine2 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm2")
            .medicineSeq(2)
            .medicineName("update medicine2")
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MedicineReqBody medicine3 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm3")
            .medicineSeq(3)
            .medicineName("update insert medicine3")
            // STATUSのenum作成しておく
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    List<MedicineReqBody> medicineList = new ArrayList<>();
    medicineList.add(medicine1);
    medicineList.add(medicine2);
    medicineList.add(medicine3);
    req.setMedicineList(medicineList);

    // TODO DBより取得する形に修正する
    BodyPartReqBody bodyPart1 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .bodyPartName("部位更新1")
            .bodyPartSeq(1)
            .status("ALIVE")
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    BodyPartReqBody bodyPart2 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb2")
            .bodyPartName("部位更新(新規追加)")
            .bodyPartSeq(2)
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    List<BodyPartReqBody> bodyPartsList = new ArrayList<>();
    bodyPartsList.add(bodyPart1);
    bodyPartsList.add(bodyPart2);
    req.setBodyPartsList(bodyPartsList);
    painRecordUseCase.updatePainRecord(req);
  }

  @Test
  @DisplayName("痛み記録を1件登録できる")
  @DatabaseSetup(value = "/application/painrecord/setup_create_a_record.xml")
  @ExpectedDatabase(
      value = "/application/painrecord/expected_create_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testCreatePainRecord() {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setPainLevel(PainLevel.MODERATE.getCode());
    req.setMemo("create test");
    req.setCreatedAt(TestDatetimeHelper.getTestDatetime());
    req.setUpdatedAt(TestDatetimeHelper.getTestDatetime());

    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .medicineSeq(1)
            .medicineName("薬登録1")
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    BodyPartReqBody bodyPart1 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .bodyPartName("部位登録1")
            .bodyPartSeq(1)
            .status("ALIVE")
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    List<MedicineReqBody> medicineList = new ArrayList<>();
    List<BodyPartReqBody> bodyPartList = new ArrayList<>();
    medicineList.add(medicine1);
    bodyPartList.add(bodyPart1);
    req.setMedicineList(medicineList);
    req.setBodyPartsList(bodyPartList);

    painRecordUseCase.createPainRecord(req);
  }

  @Test
  @DisplayName("痛み記録一覧を取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/painrecord/setup_get_records.xml")
  public void testGetPainRecordList() {
    List<PainRecordDto> expected = new ArrayList<>();
    PainRecordDto painRecord1 =
        PainRecordDto.builder()
            .painRecordId("ppppppppppppppppppppppppppppppppppp1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .painLevel(PainLevel.WORST_PAIN_POSSIBLE.getCode())
            .memo("test")
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();
    PainRecordDto painRecord2 =
        PainRecordDto.builder()
            .painRecordId("ppppppppppppppppppppppppppppppppppp2")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .painLevel(PainLevel.MODERATE.getCode())
            .memo("test")
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();
    expected.add(painRecord1);
    expected.add(painRecord2);

    List<PainRecordDto> actual =
        painRecordUseCase.getPainRecordList("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1");

    assertIterableEquals(expected, actual);
  }

  @Test
  @DisplayName("痛み記録を1件削除できる")
  @DatabaseSetup(value = "/application/painrecord/setup_delete_a_record.xml")
  @ExpectedDatabase(
      value = "/application/painrecord/expected_delete_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testDeletePainRecord() {
    painRecordUseCase.deletePainRecord("ppppppppppppppppppppppppppppppppppp1");
  }
}
