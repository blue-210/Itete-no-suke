package app.itetenosuke.controller.home;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
	@Autowired
	private MockMvc mock;
	
	@Test
	@WithUserDetails(value="test1234@gmail.com")
	@DisplayName("ホーム画面表示テスト")
	void testGetHome() throws Exception {
		mock.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("いててのすけ")));
	}

}
