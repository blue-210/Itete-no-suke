package app.itetenosuke.api.application.bodypart;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import app.itetenosuke.api.domain.shared.Status;
import shared.TestDatetimeHelper;

@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  WithSecurityContextTestExecutionListener.class
})
@Transactional
class BodyPartUseCaseTest {
  @Autowired BodyPartUseCase bodyPartUseCase;

  @Test
  @DisplayName("部位一覧を取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/bodypart/setup_get_bodyParts.xml")
  void testGetBodyPartList() {
    List<BodyPartDto> expected = new ArrayList<>();
    BodyPartDto bodyPart1 =
        BodyPartDto.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .bodyPartName("一覧取得 部位1")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();
    BodyPartDto bodyPart2 =
        BodyPartDto.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb2")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .bodyPartName("一覧取得 部位2")
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();
    expected.add(bodyPart1);
    expected.add(bodyPart2);

    List<BodyPartDto> actual =
        bodyPartUseCase.getBodyPartList("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1");

    assertThat(actual.size(), is(expected.size()));
    assertIterableEquals(expected, actual);
  }

  @Test
  @DisplayName("部位を取得できる")
  @WithUserDetails(value = "test@gmail.com")
  @DatabaseSetup(value = "/application/bodypart/setup_get_a_bodypart.xml")
  void testGetBodyPart() {
    BodyPartDto expected =
        BodyPartDto.builder()
            .bodyPartId("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1")
            .userId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu1")
            .bodyPartName("一覧取得 部位1")
            .bodyPartSeq(1)
            .status(Status.ALIVE.toString())
            .createdAt(TestDatetimeHelper.getTestDatetime())
            .updatedAt(TestDatetimeHelper.getTestDatetime())
            .build();

    BodyPartDto actual = bodyPartUseCase.getBodyPart("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1");
    assertAll(
        "actual",
        () -> assertThat(actual.getBodyPartId(), is(expected.getBodyPartId())),
        () -> assertThat(actual.getBodyPartName(), is(expected.getBodyPartName())),
        () -> assertThat(actual.getStatus(), is(expected.getStatus())));
  }
}
