package app.itetenosuke.api.presentation.controller.bodypart;

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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.presentation.controller.shared.BodyPartReqBody;
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
class BodyPartControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;

  @Test
  @DisplayName("部位一覧取得APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/bodypart/setup_get_bodyparts.xml")
  void testGetBodyPartList() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/v1/bodyparts").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(BodyPartControllerTest.class, "get_bodyparts");
    helper.writeOrCompare(result);
  }

  @Test
  @DisplayName("部位取得APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/bodypart/setup_get_a_bodypart.xml")
  void testGetBodyPart() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/v1/bodyparts/bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(BodyPartControllerTest.class, "get_a_bodypart");
    helper.writeOrCompare(result);
  }

  @Test
  @DisplayName("部位登録APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/bodypart/setup_create_a_bodypart.xml")
  void testCreateBodyPart() throws Exception {
    BodyPartReqBody bodyPart1 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .bodyPartName("部位登録1")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/v1/bodyparts")
                    .with(csrf())
                    .content(mapper.writeValueAsString(bodyPart1))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(BodyPartControllerTest.class, "create_a_bodypart");
    helper.writeOrCompare(result);
  }

  @Test
  @DisplayName("部位更新APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/bodypart/setup_update_a_bodypart.xml")
  void testUpdateBodyPart() throws Exception {
    BodyPartReqBody bodyPart1 =
        BodyPartReqBody.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .bodyPartName("部位更新")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.put("/v1/bodyparts/bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
                    .with(csrf())
                    .content(mapper.writeValueAsString(bodyPart1))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(BodyPartControllerTest.class, "update_a_bodypart");
    helper.writeOrCompare(result);
  }

  @Test
  @DisplayName("部位削除APIで期待するJSONが取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup("/presentation/controller/bodypart/setup_delete_a_bodypart.xml")
  void testDeleteBodyPart() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/v1/bodyparts/bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(BodyPartControllerTest.class, "delete_a_bodypart");
    helper.writeOrCompare(result);
  }
}
