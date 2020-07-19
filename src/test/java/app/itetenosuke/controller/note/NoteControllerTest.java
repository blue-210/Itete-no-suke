package app.itetenosuke.controller.note;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import app.itetenosuke.domain.note.model.PainLevel;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {
  @Autowired
  MockMvc mockMvc;

  private static Map<String, Integer> painLevelMap = new LinkedHashMap<>();
  static {
    painLevelMap.put(PainLevel.NO_PAIN.getName(), PainLevel.NO_PAIN.getCode());
    painLevelMap.put(PainLevel.MODERATE.getName(), PainLevel.MODERATE.getCode());
    painLevelMap.put(PainLevel.VERY_SEVERE_PAIN.getName(), PainLevel.VERY_SEVERE_PAIN.getCode());
    painLevelMap.put(PainLevel.WORST_PAIN_POSSIBLE.getName(),
        PainLevel.WORST_PAIN_POSSIBLE.getCode());
  }

  @Test
  @WithUserDetails(value = "test1234@gmail.com")
  @DisplayName("痛み記録入力画面表示テスト")
  void testShowBlankNote() throws Exception {
    mockMvc.perform(get("/note/add")).andExpect(status().isOk())
        .andExpect(model().attribute("painLevelMap", painLevelMap))
        // TODO 痛みレベルのデフォルトが0であることの確認
        .andExpect(content().string(containsString("おしまい!")));
  }

  @Test
  @WithUserDetails(value = "test1234@gmail.com")
  @DisplayName("痛み記録作成テスト")
  void testPostNoteCreate() throws Exception {}

}
