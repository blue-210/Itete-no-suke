package app.itetenosuke.repository.common;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.common.repository.ImageDao;
import app.itetenosuke.domain.note.model.NoteForm;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ImageDaoTest {
  @Autowired
  @Qualifier("ImageDaoNamedJdbcImpl")
  ImageDao imageDao;

  @BeforeEach
  void setUp() {

  }

  @Test
  @WithUserDetails(value = "test1234@gmail.com")
  // @Sql("")
  @DisplayName("画像パスを1件取得する")
  void testGetImagesPath() throws Exception {
    Image expected = new Image();
    expected.setImagePath("src/main/resources/images/1/1-848068945.jpeg");

    NoteForm noteForm = new NoteForm();
    noteForm.setUserId(Long.valueOf(1));
    noteForm.setNoteId(Long.valueOf(4));

    List<Image> imageList = imageDao.getImagesPath(noteForm);
    for (Image image : imageList) {
      assertAll("image", () -> assertEquals(expected.getImagePath(), image.getImagePath()));
    }
  }

}
