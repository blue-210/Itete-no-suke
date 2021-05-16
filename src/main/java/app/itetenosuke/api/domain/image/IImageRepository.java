package app.itetenosuke.api.domain.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IImageRepository {
  public List<Image> save(List<MultipartFile> files);
}
