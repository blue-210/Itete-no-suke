package app.itetenosuke.api.presentation.controller.medicine;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.presentation.controller.shared.MedicineReqBody;
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
class MedicineControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;

  @Test
  @DisplayName("お薬一覧取得APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/medicine/setup_get_medicines.xml")
  void testGetMedicineList() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/v1/medicines").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(MedicineControllerTest.class, "get_medicines");
    helper.writeOrCompare(result);
  }

  @Test
  @DisplayName("お薬一覧登録APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/medicine/setup_create_a_medicine.xml")
  void testCreateMedicine() throws Exception {
    MedicineReqBody medicine1 =
        MedicineReqBody.builder()
            .medicineId("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm3")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .medicineName("登録 薬")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/v1/medicines")
                    .with(csrf())
                    .content(mapper.writeValueAsString(medicine1))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(MedicineControllerTest.class, "create_a_medicine");
    helper.writeOrCompare(result);
  }
}
