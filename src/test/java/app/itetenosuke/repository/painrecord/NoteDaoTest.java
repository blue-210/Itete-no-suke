package app.itetenosuke.repository.painrecord;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.note.repository.PainRecordDao;
import app.itetenosuke.domain.painrecord.PainLevel;
import app.itetenosuke.domain.painrecord.PainRecord;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteDaoTest {
  @Autowired
  @Qualifier("NoteDaoNamedJdbcImpl")
  private PainRecordDao noteDao;

  protected final static Logger logger = LoggerFactory.getLogger(NoteDaoTest.class);

  @Disabled
  @Test
  @WithUserDetails(value = "test1234@gmail.com")
  @DisplayName("ノートを1件取得するテスト")
  public void testGetNote() throws Exception {
    Medicine expectedMedicine = new Medicine();
    expectedMedicine.setMedicineName("お薬1");

    PainRecord result = noteDao.getPainRecord(Long.valueOf(1), Long.valueOf(4));
    assertAll("result", () -> assertEquals(Integer.valueOf(0), result.getPainLevel()),
        () -> assertEquals("登録テスト", result.getMemo()),
        () -> assertEquals(expectedMedicine.getMedicineName(),
            result.getMedicineList().get(0).getMedicineName()));
  }

  @Test
  @WithUserDetails(value = "test1234@gmail.com")
  @DisplayName("ノートを1件登録するテスト")
  public void testCreateNote() throws Exception {
    PainRecord note = new PainRecord();
    note.setUserID(Long.valueOf(1));
    note.setPainLevel(PainLevel.NO_PAIN.getCode());
    note.setMemo("登録テスト");

    List<Medicine> medicineList = new ArrayList<>();
    Medicine medicine = new Medicine();
    medicine.setMedicineName("お薬1");
    medicine.setMedicineSeq(1);
    medicineList.add(medicine);
    note.setMedicineList(medicineList);

    List<BodyParts> partsList = new ArrayList<>();
    BodyParts parts = new BodyParts();
    parts.setBodyPartsName("むこうずね");
    parts.setBodyPartsSeq(1);
    partsList.add(parts);
    note.setBodyPartsList(partsList);

    List<Image> imageList = new ArrayList<>();
    Image image = new Image();
    image.setImagePath("src/main/resources/images/1/1-848068945.jpeg");
    image.setImageSeq(1);
    imageList.add(image);
    note.setImageList(imageList);

    PainRecord createdNote =
        noteDao.getPainRecord(note.getUserID(), noteDao.createPainRecord(note));
    assertAll("createdNote", () -> assertEquals(note.getPainLevel(), createdNote.getPainLevel()),
        () -> assertEquals(note.getMemo(), createdNote.getMemo()),
        () -> assertEquals(parts.getBodyPartsName(),
            createdNote.getBodyPartsList().get(0).getBodyPartsName()),
        () -> assertEquals(medicine.getMedicineName(),
            createdNote.getMedicineList().get(0).getMedicineName()),
        () -> assertEquals(medicine.getMedicineSeq(),
            createdNote.getMedicineList().get(0).getMedicineSeq()),
        () -> assertEquals(parts.getBodyPartsSeq(),
            createdNote.getBodyPartsList().get(0).getBodyPartsSeq()));
  }

  @Test
  @DisplayName("薬追加なし_部位追加なし_ノートを1件編集するテスト")
  public void testEditNoteNotAddMedicineAndBodyParts() throws Exception {
    PainRecord targetNote = noteDao.getPainRecord(Long.valueOf(1), Long.valueOf(27));
    targetNote.setPainLevel(PainLevel.WORST_PAIN_POSSIBLE.getCode());
    targetNote.setMemo("編集テスト");

    targetNote.getMedicineList().get(0).setMedicineName("編集お薬");
    targetNote.getBodyPartsList().get(0).setBodyPartsName("編集むこうずね");
    targetNote.getImageList().get(0).setImagePath("edittest");

    PainRecord editedNote =
        noteDao.getPainRecord(targetNote.getUserID(), noteDao.updatePainRecord(targetNote));
    assertAll("editedNote",
        () -> assertEquals(targetNote.getPainLevel(), editedNote.getPainLevel()),
        () -> assertEquals(targetNote.getMemo(), editedNote.getMemo()),
        () -> assertEquals(targetNote.getBodyPartsList().get(0).getBodyPartsName(),
            editedNote.getBodyPartsList().get(0).getBodyPartsName()),
        () -> assertEquals(targetNote.getBodyPartsList().get(0).getBodyPartsSeq(),
            editedNote.getBodyPartsList().get(0).getBodyPartsSeq()),
        () -> assertEquals(targetNote.getMedicineList().get(0).getMedicineName(),
            editedNote.getMedicineList().get(0).getMedicineName()),
        () -> assertEquals(targetNote.getMedicineList().get(0).getMedicineSeq(),
            editedNote.getMedicineList().get(0).getMedicineSeq()));
  }

  @Test
  @DisplayName("薬追加1_部位追加1_ノートを1件編集するテスト")
  public void testEditNoteAddOneMedicineAndBodyParts() throws Exception {
    PainRecord targetNote = noteDao.getPainRecord(Long.valueOf(1), Long.valueOf(27));
    targetNote.setPainLevel(PainLevel.WORST_PAIN_POSSIBLE.getCode());
    targetNote.setMemo("編集テスト");

    targetNote.getMedicineList().get(0).setMedicineName("編集お薬");
    targetNote.getBodyPartsList().get(0).setBodyPartsName("編集むこうずね");
    targetNote.getImageList().get(0).setImagePath("edittest");

    PainRecord editedNote =
        noteDao.getPainRecord(targetNote.getUserID(), noteDao.updatePainRecord(targetNote));
    assertAll("editedNote",
        () -> assertEquals(targetNote.getPainLevel(), editedNote.getPainLevel()),
        () -> assertEquals(targetNote.getMemo(), editedNote.getMemo()),
        () -> assertEquals(targetNote.getBodyPartsList().get(0).getBodyPartsName(),
            editedNote.getBodyPartsList().get(0).getBodyPartsName()),
        () -> assertEquals(targetNote.getBodyPartsList().get(0).getBodyPartsSeq(),
            editedNote.getBodyPartsList().get(0).getBodyPartsSeq()),
        () -> assertEquals(targetNote.getMedicineList().get(0).getMedicineName(),
            editedNote.getMedicineList().get(0).getMedicineName()),
        () -> assertEquals(targetNote.getMedicineList().get(0).getMedicineSeq(),
            editedNote.getMedicineList().get(0).getMedicineSeq()));
  }
}
