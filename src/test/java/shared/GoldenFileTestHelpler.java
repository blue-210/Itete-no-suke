package shared;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import shared.exception.FirtstJsonOutputException;

public class GoldenFileTestHelpler {
  private static ObjectMapper objectMapper =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  private final String packageName = "app.itetenosuke.api";
  private final String filePath;
  private final String fileName;

  public GoldenFileTestHelpler(Class<?> clazz, String fileName) {
    this.filePath = clazz.getPackageName().replace(packageName, "").replaceAll("\\.", "/");
    this.fileName = fileName;
  }

  public void writeOrCompare(MvcResult result) throws Exception {
    JsonNode resultJsonNode =
        objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));

    Path tempOutputPath = Paths.get("./target/test-output/" + filePath, fileName);
    try {
      Files.createDirectories(tempOutputPath.getParent());
      objectMapper.writeValue(tempOutputPath.toFile(), resultJsonNode);
    } catch (IOException e) {
      // TODO: handle exception
    }

    String expectedFilePath = "src/test/resources/presentation/painrecord/get_a_record.json";
    File expectedFile = new File(expectedFilePath);
    if (expectedFile.exists()) {
      assertThat(tempOutputPath.toFile()).hasSameTextualContentAs(expectedFile);
    } else {
      try {
        Files.copy(tempOutputPath, expectedFile.toPath());
      } catch (Exception e) {
        // TODO: handle exception
      }
      throw new FirtstJsonOutputException(
          "Golden file not found and reated golden file : " + expectedFile.getAbsolutePath());
    }
  }
}
