package app.itetenosuke.api.presentation.controller.painrecord;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.itetenosuke.api.domain.painrecord.PainLevel;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.presentation.controller.shared.BodyPartReqBody;
import app.itetenosuke.api.presentation.controller.shared.MedicineReqBody;
import app.itetenosuke.api.presentation.controller.shared.PainRecordReqBody;
import shared.GoldenFileTestHelpler;
import shared.TestDatetimeHelper;

@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  WithSecurityContextTestExecutionListener.class
})
@AutoConfigureMockMvc
@Transactional
public class PainRecordControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;

  @Test
  @DisplayName("痛み記録取得APIで期待するJSONが取得できる")
  @DatabaseSetup("/presentation/controller/painrecord/setup_get_a_record.xml")
  void testGetPainRecord() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/v1/painrecords/ppppppppppppppppppppppppppppppppppp1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(PainRecordControllerTest.class, "get_a_record");
    helper.writeOrCompare(result);
  }

  @Test
  @DisplayName("痛み記録一覧取得APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/painrecord/setup_get_records.xml")
  void testGetPainRecordList() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/v1/painrecords")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helpler =
        new GoldenFileTestHelpler(PainRecordControllerTest.class, "get_painrecords");
    helpler.writeOrCompare(result);
  }

  @Test
  @DisplayName("痛み記録更新APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/painrecord/setup_update_a_record.xml")
  @ExpectedDatabase(
      value = "/presentation/controller/painrecord/expected_update_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  void testUpdatePainRecord() throws Exception {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setUserId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1");
    req.setPainLevel(PainLevel.VERY_SEVERE_PAIN.getCode());
    req.setMemo("update test");
    req.setCreatedAt(TestDatetimeHelper.getTestDatetime());
    req.setUpdatedAt(TestDatetimeHelper.getTestDatetime());

    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .medicineSeq(1)
            .medicineName("update medicine1")
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MedicineReqBody medicine2 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm2")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .medicineSeq(2)
            .medicineName("update medicine2")
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MedicineReqBody medicine3 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm3")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
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
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .bodyPartName("部位更新1")
            .bodyPartSeq(1)
            .status("ALIVE")
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    BodyPartReqBody bodyPart2 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb2")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
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

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.put("/v1/painrecords/123456789012345678901234567890123456")
                    .with(csrf())
                    .content(mapper.writeValueAsString(req))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helpler =
        new GoldenFileTestHelpler(PainRecordControllerTest.class, "update_a_painrecord");
    helpler.writeOrCompare(result);
  }

  @Test
  @DisplayName("痛み記録登録APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/painrecord/setup_create_a_record.xml")
  @ExpectedDatabase(
      value = "/presentation/controller/painrecord/expected_create_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testCreatePainRecord() throws Exception {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setUserId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1");
    req.setPainLevel(PainLevel.MODERATE.getCode());
    req.setMemo("create test");
    req.setCreatedAt(TestDatetimeHelper.getTestDatetime());
    req.setUpdatedAt(TestDatetimeHelper.getTestDatetime());

    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .medicineSeq(1)
            .medicineName("薬登録1")
            .status(Status.ALIVE.name())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    BodyPartReqBody bodyPart1 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
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

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/v1/painrecords")
                    .with(csrf())
                    .content(mapper.writeValueAsString(req))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helpler =
        new GoldenFileTestHelpler(PainRecordControllerTest.class, "create_a_painrecord");
    helpler.writeOrCompare(result);
  }

  @Test
  @DisplayName("痛み記録削除APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/painrecord/setup_delete_a_record.xml")
  @ExpectedDatabase(
      value = "/presentation/controller/painrecord/expected_delete_a_record.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testDeletePainRecord() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.delete(
                        "/v1/painrecords/ppppppppppppppppppppppppppppppppppp1")
                    .with(csrf()))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andReturn();

    GoldenFileTestHelpler helpler =
        new GoldenFileTestHelpler(PainRecordControllerTest.class, "delete_a_painrecord");
    helpler.writeOrCompare(result);
  }
}
