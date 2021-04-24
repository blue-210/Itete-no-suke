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

import app.itetenosuke.application.painrecord.PainRecordDto;
import app.itetenosuke.application.painrecord.PainRecordUseCase;
import app.itetenosuke.domain.painrecord.PainLevel;
import app.itetenosuke.presentation.controller.painrecord.PainRecordReqBody;
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
  // TODO jOOQのバナー表示をなくす
  @Test
  @DisplayName("痛み記録を1件取得できる")
  @DatabaseSetup("/painrecord/setup_get_a_record.xml")
  public void testGetPainRecord() {
    PainRecordDto result = painRecordUseCase.getPainRecord("123456789012345678901234567890123456");
    assertAll(
        "result",
        () -> assertThat(result.getPainRecordId(), is("123456789012345678901234567890123456")),
        () -> assertThat(result.getPainLevel(), is(3)),
        () -> assertThat(result.getMemo(), is("test")));
  }

  @Test
  @DisplayName("痛み記録を1件更新できる")
  @DatabaseSetup(value = "/painrecord/setup_update_a_record.xml")
  @ExpectedDatabase(
      value = "/painrecord/expected_update_a_record.xml",
      table = "pain_records",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testUpdatePainRecord() {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setPainLevel(PainLevel.VERY_SEVERE_PAIN.getCode());
    req.setMemo("update test");
    req.setCreatedAt(LocalDateTime.now());
    req.setUpdatedAt(LocalDateTime.now());
    assertThat(painRecordUseCase.updatePainRecord(req), is(true));
  }

  @Test
  @DisplayName("痛み記録を1件登録できる")
  @DatabaseSetup(value = "/painrecord/setup_create_a_record.xml")
  @ExpectedDatabase(
      value = "/painrecord/expected_create_a_record.xml",
      table = "pain_records",
      assertionMode = DatabaseAssertionMode.NON_STRICT)
  public void testCreatePainRecord() {
    PainRecordReqBody req = new PainRecordReqBody();
    req.setPainRecordId("123456789012345678901234567890123456");
    req.setPainLevel(PainLevel.MODERATE.getCode());
    req.setMemo("create test");
    req.setCreatedAt(LocalDateTime.now());
    req.setUpdatedAt(LocalDateTime.now());
    assertThat(painRecordUseCase.createPainRecord(req), is(true));
  }
}
