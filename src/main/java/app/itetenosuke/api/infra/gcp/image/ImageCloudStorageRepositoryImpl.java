package app.itetenosuke.api.infra.gcp.image;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import app.itetenosuke.GCPConfig;
import app.itetenosuke.api.domain.image.IImageRepository;
import app.itetenosuke.api.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class ImageCloudStorageRepositoryImpl implements IImageRepository {
  private final GCPConfig config;

  @Override
  public List<Image> save(List<MultipartFile> files) {
    if (Objects.isNull(files) || files.size() == 0) {
      return Collections.emptyList();
    }
    Storage storage =
        StorageOptions.newBuilder().setProjectId(config.getProjectId()).build().getService();

    return IntStream.range(0, files.size())
        .mapToObj(
            index -> {
              String objectName = "";
              try {
                objectName = createObjectName(files.get(index));
                BlobId blodId = BlobId.of(config.getBucketName(), objectName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blodId).build();
                storage.create(blobInfo, files.get(index).getBytes());
              } catch (Exception e) {
                log.error(e.getMessage(), e);
              }
              return Image.builder().imagePath(objectName).imageSeq(index + 1).build();
            })
        .collect(Collectors.toList());
  }

  private String createObjectName(MultipartFile file) throws Exception {
    if (!isValidFileExtension(FilenameUtils.getExtension(file.getOriginalFilename()))) {
      throw new Exception("Invalid File Extension");
    }
    return "images/"
        + UUID.randomUUID().toString()
        + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
  }

  private boolean isValidFileExtension(String ext) {
    List<String> extList = Arrays.asList("jpg", "jpeg", "png", "gif");
    return extList.stream().anyMatch(v -> ext.toLowerCase().equals(v));
  }
}
