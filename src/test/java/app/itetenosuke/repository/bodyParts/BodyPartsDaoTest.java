package app.itetenosuke.repository.bodyParts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.bodyParts.repository.BodyPartsDao;

@SpringBootTest
class BodyPartsDaoTest {
	@Autowired
	@Qualifier("BodyPartsDaoNamedJdbcImpl")
	BodyPartsDao bodyPartsDao;

	@Test
	@Sql("/bodyparts/testdata_bodypartslist.sql")
	@Transactional
	@DisplayName("痛い部位一覧を取得するできること")
	void testGetBodyPartsList() {
		List<BodyParts> actual = bodyPartsDao.getBodyPartsList(Long.valueOf(1));
		assertEquals(2, actual.size());
	}

	@Test
	@Sql("/bodyparts/testdata_no_bodyparts.sql")
	@Transactional
	@DisplayName("痛い部位を1件登録できること")
	void testAddBodyParts() {
		BodyParts bodyparts = new BodyParts();
		bodyparts.setUserId(Long.valueOf(1));
		bodyparts.setBodyPartsName("痛い部位登録テスト");
		
		bodyPartsDao.addBodyParts(bodyparts);
		
		List<BodyParts> actual = bodyPartsDao.getBodyPartsList(Long.valueOf(1));
		assertEquals(1, actual.size());
	}
	
	@Test
	@Sql("/bodyparts/testdata_bodypartslist.sql")
	@DisplayName("痛い部位を1件削除できること")
	void testDeleteBodyParts() {
		List<BodyParts> list = bodyPartsDao.getBodyPartsList(Long.valueOf(1));
		list.get(0).setUserId(Long.valueOf(1));
		
		boolean result = bodyPartsDao.deleteBodyParts(list.get(0));
		
		assertEquals(true, result);
	}
	
	@Test
	@DisplayName("痛い部位を1件削除するときにユーザーIDがないと削除できない")
	void testDeleteBodyPartsFailedWithoutUserId() {
		BodyParts bodyparts = new BodyParts();
		bodyparts.setBodyPartsId(Long.valueOf(1));
		
		boolean result = bodyPartsDao.deleteBodyParts(bodyparts);
		
		assertEquals(false, result);
	}
	
	@Test
	@DisplayName("痛い部位を1件削除するときにユーザーIDがないと削除できない")
	void testDeleteBodyPartsFailedWithoutBodyPartsId() {
		BodyParts bodyparts = new BodyParts();
		bodyparts.setUserId(Long.valueOf(1));
		
		boolean result = bodyPartsDao.deleteBodyParts(bodyparts);
		
		assertEquals(false, result);
	}
	
	@Test
	@DisplayName("痛い部位を1件編集することができる")
	void testEditBodyParts() {
		List<BodyParts> list = bodyPartsDao.getBodyPartsList(Long.valueOf(1));
		list.get(0).setUserId(Long.valueOf(1));
		list.get(0).setBodyPartsName("編集テスト");
		
		boolean result = bodyPartsDao.editBodyParts(list.get(0));
		
		assertEquals(true, result);
	}
}
