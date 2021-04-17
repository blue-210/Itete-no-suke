package app.itetenosuke.api.application.painrecord;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import app.itetenosuke.api.domain.repository.painrecord.PainRecordRepository;
import app.itetenosuke.api.infrastructure.db.painrecord.PainRecordDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
class PainRecordUseCaseTest {
  @Autowired
  private PainRecordRepository painRecordRepository;

  @Test
  @DisplayName("痛み記録を1件取得できる")
  @Sql("/painrecord/test_data_get_a_record.sql")
  void testGetPainRecord() {
    Optional<PainRecordDataModel> result = painRecordRepository.findById(1L);
    assertAll("result", () -> assertEquals(3, result.orElse(null).getPainLevel()),
        () -> assertEquals("test", result.orElse(null).getMemo()));
  }
}
