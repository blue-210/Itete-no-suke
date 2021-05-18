package app.itetenosuke.api.presentation.controller.painrecord;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.itetenosuke.api.domain.image.Image;
import app.itetenosuke.api.domain.painrecord.PainLevel;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.infra.gcp.image.ImageCloudStorageRepositoryImpl;
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
  WithSecurityContextTestExecutionListener.class,
  MockitoTestExecutionListener.class
})
@AutoConfigureMockMvc
@Transactional
public class PainRecordControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;
  @MockBean ImageCloudStorageRepositoryImpl mockRepository;

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

    MockMultipartFile file1 =
        new MockMultipartFile(
            "imageFiles",
            "myicon2.jpg",
            MediaType.MULTIPART_FORM_DATA_VALUE,
            getImageByte("/presentation/controller/painrecord/myicon2.jpg"));

    MockMultipartFile file2 =
        new MockMultipartFile(
            "imageFiles",
            "myicon2.jpg",
            MediaType.MULTIPART_FORM_DATA_VALUE,
            getImageByte("/presentation/controller/painrecord/myicon2.jpg"));

    MockMultipartFile json =
        new MockMultipartFile(
            "painRecordRequest",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            mapper.writeValueAsBytes(req));

    Image imageMock1 =
        Image.builder()
            .imageId("90fe6e47-ab81-43af-8588-5ec1b7340233")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .imagePath(
                "https://storage.googleapis.com/download/storage/v1/b/itete-no-suke/o/images%2F90fe6e47-ab81-43af-8588-5ec1b7340233.jpg?generation=1621151438532456&alt=media")
            .imageSeq(1)
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    Image imageMock2 =
        Image.builder()
            .imageId("11c9f43e-fd08-434e-a0e7-8bca55f61d29")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .imagePath(
                "https://storage.googleapis.com/download/storage/v1/b/itete-no-suke/o/images%2F11c9f43e-fd08-434e-a0e7-8bca55f61d29.jpg?generation=1621151438749934&alt=media")
            .imageSeq(2)
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    List<Image> mockImages = List.of(imageMock1, imageMock2);
    when(mockRepository.save(any(PainRecord.class))).thenReturn(mockImages);

    MvcResult result =
        this.mockMvc
            .perform(
                multipart("/v1/painrecords")
                    .file(file1)
                    .file(file2)
                    .file(json)
                    .with(csrf())
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
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

  private byte[] getImageByte(String path) throws IOException {
    try (InputStream in = new ClassPathResource(path).getInputStream(); ) {
      byte[] buf = FileCopyUtils.copyToByteArray(in);
      return buf;
    }
  }
}
