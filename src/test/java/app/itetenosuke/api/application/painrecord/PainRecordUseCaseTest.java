package app.itetenosuke.api.application.painrecord;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;

import app.itetenosuke.application.painrecord.PainRecordUseCase;
import app.itetenosuke.domain.painrecord.PainRecordRepository;
import app.itetenosuke.infrastructure.db.painrecord.PainRecordDataModel;
import app.itetenosuke.presentation.model.PainRecordRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
@ExtendWith(SpringExtension.class)
// @DataJpaTest
class PainRecordUseCaseTest {
  @Autowired
  private PainRecordUseCase painRecordUseCase;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private PainRecordRepository painRecordRepository;

  @Test
  @DisplayName("痛み記録を1件取得できる")
  @DatabaseSetup("/painrecord/setup_get_a_record.xml")
  @ExpectedDatabase(value = "/painrecord/expected_get_a_record.xml", table = "pain_records")
  public void testGetPainRecord() {
    PainRecordDataModel result = painRecordUseCase.getPainRecord(1L);
  }

  @Test
  @DisplayName("痛み記録を1件登録できる")
  @DatabaseSetup(type = DatabaseOperation.DELETE_ALL,
      value = "/painrecord/setup_create_a_record.xml")
  @ExpectedDatabase(value = "/painrecord/expected_create_a_record.xml", table = "pain_records",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testCreatePainRecord() {
    PainRecordRequest req = new PainRecordRequest();
    req.setPainLevel(2);
    req.setMemo("create test");
    req.setCreatedAt(LocalDateTime.now());
    req.setUpdatedAt(LocalDateTime.now());
    PainRecordDataModel result = painRecordUseCase.createPainRecord(req);
    // JPAやHibernateを使った更新系のテストの場合、永続コンテキストを明示的にフラッシュする必要がある
    entityManager.flush();
  }

  @Test
  @DisplayName("痛み記録を1件更新できる")
  @DatabaseSetup("/painrecord/setup_update_a_record.xml")
  @ExpectedDatabase(value = "/painrecord/expected_update_a_record.xml", table = "pain_records",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testUpdatePainRecord() {
    PainRecordRequest req = new PainRecordRequest();
    req.setPainRecordID(1L);
    req.setPainLevel(2);
    req.setMemo("update test");
    req.setUpdatedAt(LocalDateTime.now());
    PainRecordDataModel result = painRecordUseCase.updatePainRecord(req);
    entityManager.flush();
  }
}
