package app.itetenosuke.api.presentation.controller.bodypart;

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

import shared.GoldenFileTestHelpler;

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
}
