package app.itetenosuke.controller.bodyParts;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.bodyParts.model.BodyParts;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BodyPartsControllerTest {
	@Autowired
	private MockMvc mock;
	
	@Test
	@WithUserDetails
	@DisplayName("いたいところ入力画面が表示できること")
	void testShowBodyParts() throws Exception {
		mock.perform(get("/bodyparts"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("いたいところとうろく")));
	}

	@Test
	@Sql("/bodyparts/testdata_bodypartslist.sql")
	@Transactional
	@WithUserDetails
	@DisplayName("いたいところ入力画面が表示できること")
	void testShowBodyPartsList() throws Exception {
		MvcResult result = mock.perform(get("/bodyparts"))
			.andExpect(status().isOk())
			.andReturn();
		
		@SuppressWarnings("unchecked")
		List<BodyParts> actual = (List<BodyParts>)result.getModelAndView().getModel().get("bodyPartsList");
		assertEquals(2, actual.size());
	}
}

