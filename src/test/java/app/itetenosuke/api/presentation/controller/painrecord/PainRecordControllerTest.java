package app.itetenosuke.api.presentation.controller.painrecord;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import shared.GoldenFileTestHelpler;

@SpringBootTest
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
public class PainRecordControllerTest {
  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("痛み記録取得APIで期待するJSONが取得できる")
  @DatabaseSetup("/presentation/painrecord/setup_get_a_record.xml")
  void testGetPainRecord() throws Exception {
    // 期待値のJSONを取得する
    //  String expected = StreamUtils.copyToString(new ClassPathResource(path), charset)
    // mockMvcでgetPainRecordを呼び出す
    MvcResult result =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/v1/painrecord/ppppppppppppppppppppppppppppppppppp1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    GoldenFileTestHelpler helper =
        new GoldenFileTestHelpler(PainRecordControllerTest.class, "get_a_record.json");
    helper.writeOrCompare(result);
  }
}
