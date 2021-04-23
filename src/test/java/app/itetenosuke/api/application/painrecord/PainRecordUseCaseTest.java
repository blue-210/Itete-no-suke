package app.itetenosuke.api.application.painrecord;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.itetenosuke.application.painrecord.PainRecordUseCase;
import app.itetenosuke.domain.painrecord.PainLevel;
import app.itetenosuke.domain.painrecord.PainRecord;
import app.itetenosuke.presentation.model.PainRecordRequest;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
@Slf4j
class PainRecordUseCaseTest {
  @Autowired private PainRecordUseCase painRecordUseCase;

  @Test
  @DisplayName("痛み記録を1件取得できる")
  @DatabaseSetup("/painrecord/setup_get_a_record.xml")
  public void testGetPainRecord() {
    PainRecord result = painRecordUseCase.getPainRecord(1L);
    assertAll(
        "result",
        () -> assertThat(result.getPainRecordID(), is(1L)),
        () -> assertThat(result.getMemo(), is("test")));
  }

  @Test
  @DisplayName("痛み記録を1件更新できる")
  @DatabaseSetup(value = "/painrecord/setup_update_a_record.xml")
  @ExpectedDatabase(
      value = "/painrecord/expected_update_a_record.xml",
      table = "pain_records",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testCreatePainRecord() {
    PainRecordRequest req = new PainRecordRequest();
    req.setPainRecordID(1L);
    req.setPainLevel(PainLevel.VERY_SEVERE_PAIN.getCode());
    req.setMemo("update test");
    req.setCreatedAt(LocalDateTime.now());
    req.setUpdatedAt(LocalDateTime.now());
    painRecordUseCase.updatePainRecord(req);
  }
  //
  //  @Test
  //  @DisplayName("痛み記録を1件更新できる")
  //  @DatabaseSetup("/painrecord/setup_update_a_record.xml")f
  //  @ExpectedDatabase(value = "/painrecord/expected_update_a_record.xml", table = "pain_records",
  //      assertionMode = DatabaseAssertionMode.NON_STRICT)
  //  public void testUpdatePainRecord() {
  //    PainRecordRequest req = new PainRecordRequest();
  //    req.setPainRecordID(1L);
  //    req.setPainLevel(2);
  //    req.setMemo("update test");
  //    req.setUpdatedAt(LocalDateTime.now());
  //    PainRecordDataModel result = painRecordUseCase.updatePainRecord(req);
  //    entityManager.flush();
  //  }
}
