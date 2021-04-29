package app.itetenosuke.domain.common.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.common.repository.ImageDao;
import app.itetenosuke.domain.painrecord.NoteForm;

@Transactional(rollbackFor = Exception.class)
@Service
public class ImageService {
  @Autowired
  @Qualifier("ImageDaoNamedJdbcImpl")
  ImageDao imageDao;

  public List<Image> getImagesPath(NoteForm noteForm) {
    return imageDao.getImagesPath(noteForm);
  }

  public List<Image> saveImages(List<MultipartFile> files, Long userId) {
    // 画像を保存
    List<Image> imagesList = new ArrayList<>();
    try {
      Path dir = Paths.get("static", "images", String.valueOf(userId)).toAbsolutePath();
      if (Files.notExists(dir)) {
        Files.createDirectories(dir);
      }

      int count = 1;
      for (MultipartFile uploadFile : files) {
        Random random = SecureRandom.getInstanceStrong();
        String originalName = uploadFile.getOriginalFilename();
        String fileName = String.valueOf(userId) + String.valueOf(random.nextInt())
            + originalName.substring(originalName.lastIndexOf("."));
        Path path =
            Paths.get("static", "images", String.valueOf(userId), fileName).toAbsolutePath();
        Files.createFile(path);
        uploadFile.transferTo(path);

        Image image = new Image();
        String pathStr = path.toString();
        image.setImagePath(pathStr.substring(pathStr.indexOf(File.separator + "images")));
        image.setImageSeq(count++);
        imagesList.add(image);
      }
    } catch (IOException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return imagesList;
  }

}
