package app.itetenosuke.api.infra.gcp.image;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Repository;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import app.itetenosuke.GCPConfig;
import app.itetenosuke.api.domain.image.IImageRepository;
import app.itetenosuke.api.domain.image.Image;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Repository(value = "ImageCloudStorageRepositoryImpl")
public class ImageCloudStorageRepositoryImpl implements IImageRepository {
  private final GCPConfig config;

  @Override
  public List<Image> save(PainRecord painRecord) {
    List<Image> imageList = painRecord.getImageList();
    if (Objects.isNull(imageList) || imageList.size() == 0) {
      return Collections.emptyList();
    }
    Storage storage =
        StorageOptions.newBuilder().setProjectId(config.getProjectId()).build().getService();

    return IntStream.range(0, imageList.size())
        .mapToObj(
            index -> {
              String imagePath = "";
              try {
                BlobId blodId =
                    BlobId.of(config.getBucketName(), createObjectName(imageList.get(index)));
                BlobInfo blobInfo = BlobInfo.newBuilder(blodId).build();
                imagePath =
                    storage
                        .create(blobInfo, imageList.get(index).getFile().getBytes())
                        .getMediaLink();
              } catch (Exception e) {
                log.error(e.getMessage(), e);
              }
              return Image.builder()
                  .imageId(imageList.get(index).getImageId())
                  .userId(painRecord.getUserId())
                  .imagePath(imagePath)
                  .imageSeq(index + 1)
                  .build();
            })
        .collect(Collectors.toList());
  }

  private String createObjectName(Image image) throws Exception {
    if (!isValidFileExtension(FilenameUtils.getExtension(image.getFile().getOriginalFilename()))) {
      throw new Exception("Invalid File Extension");
    }
    String originalFileName = image.getFile().getOriginalFilename();
    return "images/"
        + image.getImageId()
        + originalFileName.substring(originalFileName.lastIndexOf("."));
  }

  private boolean isValidFileExtension(String ext) {
    List<String> extList = Arrays.asList("jpg", "jpeg", "png", "gif");
    return extList.stream().anyMatch(v -> ext.toLowerCase().equals(v));
  }

  @Override
  public List<Image> findAllByPainRecordId(String painRecordID) { // TODO Auto-generated method stub
    return null;
  }
}
