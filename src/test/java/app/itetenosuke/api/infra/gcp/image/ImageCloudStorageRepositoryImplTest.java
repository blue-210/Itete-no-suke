package app.itetenosuke.api.infra.gcp.image;

import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import app.itetenosuke.api.domain.image.IImageRepository;

@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
})
class ImageCloudStorageRepositoryImplTest {
  @Autowired private IImageRepository imageCloudStorageRepository;

  @Test
  @DisplayName("GCPのプロジェクトIDとバケットネームが取得できる")
  void testSave() {
    imageCloudStorageRepository.save(List.of());
  }
}
