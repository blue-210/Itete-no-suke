package app.itetenosuke.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import app.itetenosuke.api.domain.user.UserDao;

@SpringBootTest
class UserDaoJdbcImplTest {
  @Autowired
  @Qualifier("UserDaoJdbcImpl")
  private UserDao userDao;

  // @Test
  // @DisplayName("ユーザーを1件登録する")
  // void testInsertOne() {
  // String birthday = "1988-06-04";
  // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  //
  // AppUser user = new AppUser();
  // user.setEmail("regist-test@gmail.com");
  // user.setUserName("登録 テスト");
  // user.setPassword("registtest123");
  // try {
  // user.setBirthday(format.parse(birthday));
  // } catch (Exception e) {
  // fail();
  // }
  // user.setAge(30);
  // user.setRole(UserRole.ROLE_GENERAL.toString());
  // user.setStatus("ALIVE");
  //
  // int result = userDao.insertOne(user);
  // assertEquals(1, result);
  // }

}
