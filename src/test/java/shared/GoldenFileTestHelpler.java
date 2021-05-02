package shared;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;
import shared.exception.FirtstJsonOutputException;

@Slf4j
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
    Path tempOutputPath = writeActualOutputToFile(resultJsonNode);
    compareActualAndExpected(tempOutputPath);
  }

  private void compareActualAndExpected(Path tempOutputPath) {
    Path expectedFilePath = Paths.get("./src/test/resources/", filePath, fileName);
    if (Files.exists(expectedFilePath)) {
      assertThat(tempOutputPath.toFile()).hasSameTextualContentAs(expectedFilePath.toFile());
    } else {
      copyActualOutputToFile(tempOutputPath, expectedFilePath);
    }
  }

  private void copyActualOutputToFile(Path tempOutputPath, Path expectedFilePath) {
    try {
      Files.createDirectories(expectedFilePath.getParent());
      Files.copy(tempOutputPath, expectedFilePath);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    throw new FirtstJsonOutputException(
        "Golden file not found and created golden file : " + expectedFilePath.toAbsolutePath());
  }

  private Path writeActualOutputToFile(JsonNode resultJsonNode) {
    Path tempOutputPath = Paths.get("./target/test-output/" + filePath, fileName);
    try {
      Files.createDirectories(tempOutputPath.getParent());
      objectMapper.writeValue(tempOutputPath.toFile(), resultJsonNode);
    } catch (IOException e) {
      // TODO: handle exception
    }
    return tempOutputPath;
  }
}
