package app.itetenosuke.domain.note.repository.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.repository.NoteDao;

@Repository("NoteDaoNamedJdbcImpl")
public class NoteDaoNamedJdbcImpl implements NoteDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbc;

  @Override
  public Integer createNote(NoteForm noteForm) throws DataAccessException {
    Integer noteId = insertNotes(noteForm);
    insertUsersNotes(noteForm, noteId);

    Map<Integer, Integer> medicineIdandSeqMap = insertMedicine(noteForm);
    insertNotesMedicine(medicineIdandSeqMap, noteId);

    Map<Integer, Integer> bodyPartsIdandSeqMap = insertBodyParts(noteForm, noteId);
    insertNotesBodyParts(bodyPartsIdandSeqMap, noteId);

    if (!noteForm.getImageList().isEmpty()) {
      Map<Integer, Integer> imagesIdandSeqMap = insertImages(noteForm, noteId);
      insertNotesImages(imagesIdandSeqMap, noteId);
    }

    return noteId;
  }

  private void insertNotesImages(Map<Integer, Integer> imagesIdandSeqMap, Integer noteId) {
    // 痛み記録_画像マスタ関連テーブル用SQL
    String sqlForNotesImages =
        "INSERT INTO notes_images ( fk_note_id, fk_images_id, images_seq) VALUES (:fk_note_id, :fk_images_id, :images_seq)";

    // パラメータ設定
    imagesIdandSeqMap.entrySet().stream().forEach(entry -> {
      SqlParameterSource paramForNotesImages =
          new MapSqlParameterSource().addValue("fk_note_id", noteId)
              .addValue("fk_images_id", entry.getKey()).addValue("images_seq", entry.getValue());

      jdbc.update(sqlForNotesImages, paramForNotesImages);
    });
  }

  private Map<Integer, Integer> insertImages(NoteForm noteForm, Integer noteId) {
    Map<Integer, Integer> mapImagesIdandSeq = new HashMap<>();
    // 画像マスタテーブル登録用SQL
    String sqlForImages =
        "INSERT INTO images (" + " images_path," + " created_at," + " updated_at" + " ) VALUES ("
            + " :images_path," + " :created_at," + " :updated_at" + ") RETURNING images_id";

    // パラメータ設定
    for (Image image : noteForm.getImageList()) {
      SqlParameterSource paramForImages =
          new MapSqlParameterSource().addValue("images_path", image.getImagePath())
              .addValue("created_at", new Timestamp(new Date().getTime()))
              .addValue("updated_at", new Timestamp(new Date().getTime()));

      mapImagesIdandSeq.put(jdbc.queryForObject(sqlForImages, paramForImages, Integer.class),
          image.getImageSeq());
    }
    return mapImagesIdandSeq;
  }


  private void insertNotesBodyParts(Map<Integer, Integer> bodyPartsIdandSeqMap, Integer noteId) {
    // 痛み記録_部位マスタ関連テーブル登録用SQL
    String sqlForNotesBodyParts =
        "INSERT INTO notes_bodyparts (fk_note_id, fk_bodyparts_id, body_parts_seq) VALUES (:fk_note_id, :fk_bodyparts_id, :body_parts_seq)";

    for (Map.Entry<Integer, Integer> bodyPartsEntry : bodyPartsIdandSeqMap.entrySet()) {
      SqlParameterSource paramForNotesBodyParts = new MapSqlParameterSource()
          .addValue("fk_note_id", noteId).addValue("fk_bodyparts_id", bodyPartsEntry.getKey())
          .addValue("body_parts_seq", bodyPartsEntry.getValue());

      jdbc.update(sqlForNotesBodyParts, paramForNotesBodyParts);
    }
  }

  private Map<Integer, Integer> insertBodyParts(NoteForm noteForm, Integer noteId) {
    Map<Integer, Integer> bodyPartsIdandSeqMap = new HashMap<>();

    // 部位マスタに登録用SQL
    String sqlForBodyParts = "INSERT INTO bodyparts (" + " body_parts_name," + " created_at,"
        + " updated_at" + ") VALUES (" + ":body_parts_name," + ":created_at," + ":updated_at"
        + ") RETURNING body_parts_id";

    // パラメータ設定
    for (BodyParts parts : noteForm.getBodyPartsList()) {
      SqlParameterSource paramForBodyParts =
          new MapSqlParameterSource().addValue("body_parts_name", parts.getBodyPartsName())
              .addValue("created_at", new Timestamp(new Date().getTime()))
              .addValue("updated_at", new Timestamp(new Date().getTime()));

      bodyPartsIdandSeqMap.put(
          jdbc.queryForObject(sqlForBodyParts, paramForBodyParts, Integer.class),
          parts.getBodyPartsSeq());
    }
    return bodyPartsIdandSeqMap;
  }

  private void insertNotesMedicine(Map<Integer, Integer> medicineIdandSeqMap, Integer noteId) {
    // 痛み記録_薬マスタ関連テーブル登録用SQL
    String sqlForNotesMedicine =
        "INSERT INTO notes_medicine (fk_note_id, fk_medicine_id, medicine_seq) VALUES (:fk_note_id, :fk_medicine_id, :medicine_seq)";

    // パラメータ設定
    for (Map.Entry<Integer, Integer> medicineEntry : medicineIdandSeqMap.entrySet()) {
      SqlParameterSource paramForNotesMedicine = new MapSqlParameterSource()
          .addValue("fk_note_id", noteId).addValue("fk_medicine_id", medicineEntry.getKey())
          .addValue("medicine_seq", medicineEntry.getValue());

      jdbc.update(sqlForNotesMedicine, paramForNotesMedicine);
    }
  }

  private Map<Integer, Integer> insertMedicine(NoteForm noteForm) {
    Map<Integer, Integer> medicineIdandSeqMap = new HashMap<>();
    // 薬マスタに登録用SQL
    String sqlForMedicine =
        "INSERT INTO medicine (" + " medicine_name," + " created_at," + " updated_at" + ") VALUES ("
            + ":medicine_name," + ":created_at," + ":updated_at" + ") RETURNING medicine_id";

    // パラメータ設定
    for (Medicine medicine : noteForm.getMedicineList()) {
      SqlParameterSource paramForMedicine =
          new MapSqlParameterSource().addValue("medicine_name", medicine.getMedicineName())
              .addValue("created_at", new Timestamp(new Date().getTime()))
              .addValue("updated_at", new Timestamp(new Date().getTime()));

      medicineIdandSeqMap.put(jdbc.queryForObject(sqlForMedicine, paramForMedicine, Integer.class),
          medicine.getMedicineSeq());
    }
    return medicineIdandSeqMap;
  }

  private void insertUsersNotes(NoteForm noteForm, Integer noteId) {
    // ユーザー_痛み記録関連テーブル登録用SQL
    String sqlForUsersNotes =
        "INSERT INTO users_notes(fk_user_id, fk_note_id) VALUES (:fk_user_id, :fk_note_id)";
    // パラメータ
    SqlParameterSource paramsForUsersNotes = new MapSqlParameterSource()
        .addValue("fk_user_id", noteForm.getUserId()).addValue("fk_note_id", noteId);

    // ユーザ痛み記録関連テーブルに登録
    jdbc.update(sqlForUsersNotes, paramsForUsersNotes);
  }

  private Integer insertNotes(NoteForm noteForm) {
    // 痛み記録登録用SQL
    String sqlForNotes = "INSERT INTO notes (" + " pain_level," + " memo," + " created_at,"
        + " updated_at" + ") VALUES (" + ":pain_level," + ":memo," + ":created_at," + ":updated_at"
        + ") RETURNING note_id";
    // パラメータ設定
    SqlParameterSource paramsForNotes = new MapSqlParameterSource()
        .addValue("pain_level", noteForm.getPainLevel()).addValue("memo", noteForm.getMemo())
        .addValue("created_at", new Timestamp(new Date().getTime()))
        .addValue("updated_at", new Timestamp(new Date().getTime()));

    // 痛み記録に登録
    Integer noteId = jdbc.queryForObject(sqlForNotes, paramsForNotes, Integer.class);
    return noteId;
  }

  @Override
  public NoteForm getNote(long userId, long noteId) throws DataAccessException {
    SqlParameterSource params =
        new MapSqlParameterSource().addValue("note_id", noteId).addValue("user_id", userId);

    Map<String, Object> map = findNote(params);
    List<Medicine> medicineList = getMedicineList(params);
    List<BodyParts> bodyPartsList = getBodyPartsList(params);
    List<Image> imageList = getImageList(params);

    NoteForm noteForm = new NoteForm();
    noteForm.setUserId(userId);
    noteForm.setNoteId((Long) map.get("note_id"));
    noteForm.setPainLevel((Integer) map.get("pain_level"));
    noteForm.setMemo((String) map.get("memo"));
    noteForm.setCreatedAt(((Timestamp) map.get("created_at")).toLocalDateTime());
    noteForm.setMedicineList(medicineList);
    noteForm.setBodyPartsList(bodyPartsList);
    noteForm.setImageList(imageList);

    return noteForm;
  }

  private List<Image> getImageList(SqlParameterSource params) {
    String sqlForImageList = "SELECT i.*, ni.images_seq "
        + " FROM notes n INNER JOIN notes_images ni ON n.note_id = ni.fk_note_id"
        + "              INNER JOIN images i ON i.images_id = ni.fk_images_id"
        + " WHERE n.note_id = :note_id";
    List<Map<String, Object>> imagesMapList = jdbc.queryForList(sqlForImageList, params);
    List<Image> imagesList = new ArrayList<>();
    for (Map<String, Object> imagesMap : imagesMapList) {
      Image image = new Image();
      image.setImageId((Long) imagesMap.get("images_id"));
      image.setImageSeq((Integer) imagesMap.get("images_seq"));
      image.setImagePath((String) imagesMap.get("images_path"));
      imagesList.add(image);
    }
    return imagesList;
  }

  private List<BodyParts> getBodyPartsList(SqlParameterSource params) {
    String sqlForBodyParts = "SELECT b.*, nb.body_parts_seq "
        + " FROM notes n INNER JOIN notes_bodyparts nb ON n.note_id = nb.fk_note_id"
        + "              INNER JOIN bodyparts b ON b.body_parts_id = nb.fk_bodyparts_id"
        + " WHERE n.note_id = :note_id";

    List<Map<String, Object>> bodyPartsMapList = jdbc.queryForList(sqlForBodyParts, params);
    List<BodyParts> bodyPartsList = new ArrayList<>();
    for (Map<String, Object> bodyPartsMap : bodyPartsMapList) {
      BodyParts parts = new BodyParts();
      parts.setBodyPartsId((Long) bodyPartsMap.get("body_parts_id"));
      parts.setBodyPartsSeq((Integer) bodyPartsMap.get("body_parts_seq"));
      parts.setBodyPartsName((String) bodyPartsMap.get("body_parts_name"));
      parts.setUpdatedAt(((Timestamp) bodyPartsMap.get("updated_at")).toLocalDateTime());
      parts.setCreatedAt(((Timestamp) bodyPartsMap.get("created_at")).toLocalDateTime());
      bodyPartsList.add(parts);
    }
    return bodyPartsList;
  }

  private List<Medicine> getMedicineList(SqlParameterSource params) {
    String sqlForMedicine = "SELECT m.*, nm.medicine_seq"
        + " FROM notes n INNER JOIN notes_medicine nm ON n.note_id = nm.fk_note_id"
        + "              INNER JOIN medicine m ON m.medicine_id = nm.fk_medicine_id"
        + " WHERE n.note_id = :note_id";

    List<Map<String, Object>> medicineMapList = jdbc.queryForList(sqlForMedicine, params);
    List<Medicine> medicineList = new ArrayList<>();
    for (Map<String, Object> medicineMap : medicineMapList) {
      Medicine medicine = new Medicine();
      medicine.setMedicineId((Long) medicineMap.get("medicine_id"));
      medicine.setMedicineSeq((Integer) medicineMap.get("medicine_seq"));
      medicine.setMedicineName((String) medicineMap.get("medicine_name"));
      medicine.setMedicineMemo((String) medicineMap.getOrDefault("medicine_mem", ""));
      medicine.setUpdatedAt(((Timestamp) medicineMap.get("updated_at")).toLocalDateTime());
      medicine.setCreatedAt(((Timestamp) medicineMap.get("created_at")).toLocalDateTime());
      medicineList.add(medicine);
    }
    return medicineList;
  }

  private Map<String, Object> findNote(SqlParameterSource params) throws DataAccessException {
    String sqlForNote =
        "SELECT n.*" + " FROM notes n INNER JOIN users_notes un ON n.note_id = un.fk_note_id"
            + "             INNER JOIN users u ON u.user_id = un.fk_user_id"
            + " WHERE n.note_id = :note_id AND u.user_id = :user_id";

    return jdbc.queryForMap(sqlForNote, params);
  }

  @Override
  public List<NoteForm> getNoteList(long userId) throws DataAccessException {
    String sql =
        "SELECT n.*" + " FROM notes n " + " INNER JOIN users_notes un ON n.note_id = un.fk_note_id"
            + " INNER JOIN users u ON u.user_id = un.fk_user_id" + " WHERE u.user_id = :user_id"
            + " ORDER BY created_at DESC";

    SqlParameterSource params = new MapSqlParameterSource().addValue("user_id", userId);

    List<Map<String, Object>> list = jdbc.queryForList(sql, params);

    List<NoteForm> noteList = new ArrayList<>();
    for (Map<String, Object> map : list) {
      SqlParameterSource paramsForLists = new MapSqlParameterSource()
          .addValue("note_id", (Long) map.get("note_id")).addValue("user_id", userId);

      List<Medicine> medicineList = getMedicineList(paramsForLists);
      List<BodyParts> bodyPartsList = getBodyPartsList(paramsForLists);
      List<Image> imageList = getImageList(paramsForLists);

      NoteForm note = new NoteForm();
      note.setNoteId((Long) map.get("note_id"));
      note.setPainLevel((Integer) map.get("pain_level"));
      note.setMedicineList(medicineList);
      note.setBodyPartsList(bodyPartsList);
      note.setImageList(imageList);
      note.setMemo((String) map.get("memo"));
      note.setCreatedAt(((Timestamp) map.get("created_at")).toLocalDateTime());
      note.setUpdatedAt(((Timestamp) map.get("created_at")).toLocalDateTime());
      noteList.add(note);
    }
    return noteList;
  }

  @Override
  public Integer editNote(NoteForm noteForm) throws DataAccessException {
    final Optional<Integer> noteId = updateNote(noteForm);
    upsertUsersNotes(noteForm, noteId);

    Map<Integer, Integer> medicineIdandSeqMap = upsertMedicine(noteForm);
    upsertNotesMedicine(noteId, medicineIdandSeqMap);

    Map<Integer, Integer> mapBodyPartsIdandSeq = upsertBodyParts(noteForm);
    upsertNotesBodyParts(noteId, mapBodyPartsIdandSeq);

    Map<Integer, Integer> mapImagesIdandSeq = upsertImages(noteForm);
    upsertNotesImages(noteId, mapImagesIdandSeq);

    return noteId.get();
  }

  private void upsertNotesImages(final Optional<Integer> noteId,
      Map<Integer, Integer> mapImagesIdandSeq) {
    // 痛み記録_画像関連マスタ登録or更新
    StringBuffer sqlForNotesImages = new StringBuffer();
    sqlForNotesImages.append("INSERT INTO notes_images (fk_note_id, fk_images_id, images_seq) ")
        .append("VALUES(:fk_note_id, :fk_images_id, :images_seq) ")
        .append("ON CONFLICT ON CONSTRAINT notes_images_pkey ").append("DO NOTHING");

    mapImagesIdandSeq.entrySet().stream().forEach(entry -> {
      SqlParameterSource paramForNotesImages =
          new MapSqlParameterSource().addValue("fk_note_id", noteId.get())
              .addValue("fk_images_id", entry.getKey()).addValue("images_seq", entry.getValue());
      jdbc.update(sqlForNotesImages.toString(), paramForNotesImages);
    });
  }

  private Map<Integer, Integer> upsertImages(NoteForm noteForm) {
    // 画像マスタ登録or更新
    StringBuffer sqlForImagesInsert = new StringBuffer();
    sqlForImagesInsert.append("INSERT INTO images ( ").append("images_path,").append("created_at, ")
        .append("updated_at ").append(") VALUES (").append(":images_path, ").append(":created_at, ")
        .append(":updated_at ").append(") RETURNING images_id ");

    StringBuffer sqlForImagesUpdate = new StringBuffer();
    sqlForImagesUpdate.append("UPDATE images SET (").append("images_path = :images_path, ")
        .append("updated_at = :updated_at ").append("WHERE images_id = :images_id ")
        .append("RETURNING images_id ");

    Map<Integer, Integer> mapImagesIdandSeq = new HashMap<>();
    noteForm.getImageList().forEach(image -> {
      if (image.getImageId() != null) {
        SqlParameterSource paramForImages = new MapSqlParameterSource()
            .addValue("images_id", image.getImageId()).addValue("images_path", image.getImagePath())
            .addValue("updated_at", new Timestamp(new Date().getTime()));
        mapImagesIdandSeq.put(
            jdbc.queryForObject(sqlForImagesUpdate.toString(), paramForImages, Integer.class),
            image.getImageSeq());
      } else {
        SqlParameterSource paramForImages =
            new MapSqlParameterSource().addValue("images_path", image.getImagePath())
                .addValue("created_at", new Timestamp(new Date().getTime()))
                .addValue("updated_at", new Timestamp(new Date().getTime()));
        mapImagesIdandSeq.put(
            jdbc.queryForObject(sqlForImagesInsert.toString(), paramForImages, Integer.class),
            image.getImageSeq());
      }
    });
    return mapImagesIdandSeq;
  }

  private void upsertNotesBodyParts(final Optional<Integer> noteId,
      Map<Integer, Integer> mapBodyPartsIdandSeq) {
    // 痛み記録_部位関連マスタ登録or更新
    StringBuffer sqlForNotesBodyParts = new StringBuffer();
    sqlForNotesBodyParts
        .append("INSERT INTO notes_bodyparts (fk_note_id, fk_bodyparts_id, body_parts_seq) ")
        .append("VALUES (:fk_note_id, :fk_bodyparts_id, :body_parts_seq) ")
        .append("ON CONFLICT ON CONSTRAINT notes_bodyparts_pkey ").append("DO NOTHING");

    mapBodyPartsIdandSeq.entrySet().stream().forEach(entry -> {
      SqlParameterSource paramForNotesBodyParts = new MapSqlParameterSource()
          .addValue("fk_note_id", noteId.get()).addValue("fk_bodyparts_id", entry.getKey())
          .addValue("body_parts_seq", entry.getValue());
      jdbc.update(sqlForNotesBodyParts.toString(), paramForNotesBodyParts);
    });
  }

  private Map<Integer, Integer> upsertBodyParts(NoteForm noteForm) {
    // 部位マスタを登録or更新
    StringBuffer sqlForBodyPartsInsert = new StringBuffer();
    sqlForBodyPartsInsert.append("INSERT INTO bodyparts (").append("body_parts_name,")
        .append("created_at,").append("updated_at").append(") VALUES (").append(":body_parts_name,")
        .append(":created_at, ").append(":updated_at").append(") RETURNING body_parts_id");

    StringBuffer sqlForBodyPartsUpdate = new StringBuffer();
    sqlForBodyPartsUpdate.append("UPDATE bodyparts SET ")
        .append(" body_parts_name = :body_parts_name, ").append(" updated_at = :updated_at ")
        .append("WHERE body_parts_id = :body_parts_id ").append("RETURNING body_parts_id");

    Map<Integer, Integer> mapBodyPartsIdandSeq = new HashMap<>();
    noteForm.getBodyPartsList().stream().forEach(bodyParts -> {
      if (bodyParts.getBodyPartsId() != null) {
        SqlParameterSource paramForBodyParts =
            new MapSqlParameterSource().addValue("body_parts_id", bodyParts.getBodyPartsId())
                .addValue("body_parts_name", bodyParts.getBodyPartsName())
                .addValue("updated_at", new Timestamp(new Date().getTime()));
        mapBodyPartsIdandSeq.put(
            jdbc.queryForObject(sqlForBodyPartsUpdate.toString(), paramForBodyParts, Integer.class),
            bodyParts.getBodyPartsSeq());
      } else {
        SqlParameterSource paramForBodyParts =
            new MapSqlParameterSource().addValue("body_parts_name", bodyParts.getBodyPartsName())
                .addValue("created_at", new Timestamp(new Date().getTime()))
                .addValue("updated_at", new Timestamp(new Date().getTime()));
        mapBodyPartsIdandSeq.put(
            jdbc.queryForObject(sqlForBodyPartsInsert.toString(), paramForBodyParts, Integer.class),
            bodyParts.getBodyPartsSeq());
      }
    });
    return mapBodyPartsIdandSeq;
  }

  private void upsertNotesMedicine(final Optional<Integer> noteId,
      Map<Integer, Integer> medicineIdandSeqMap) {
    // 痛み記録_薬関連マスタを登録or更新
    StringBuffer sqlForNotesMedicine = new StringBuffer();
    sqlForNotesMedicine
        .append("INSERT INTO notes_medicine (fk_note_id, fk_medicine_id, medicine_seq) ")
        .append("VALUES (:fk_note_id, :fk_medicine_id, :medicine_seq) ")
        .append("ON CONFLICT ON CONSTRAINT notes_medicine_pkey ").append("DO NOTHING");

    // 痛み記録_薬関連マスタのパラメータ設定
    medicineIdandSeqMap.entrySet().stream().forEach((entry) -> {
      SqlParameterSource paramForNotesMedicine = new MapSqlParameterSource()
          .addValue("fk_note_id", noteId.get()).addValue("fk_medicine_id", entry.getKey())
          .addValue("medicine_seq", entry.getValue());
      jdbc.update(sqlForNotesMedicine.toString(), paramForNotesMedicine);
    });
  }

  private Map<Integer, Integer> upsertMedicine(NoteForm noteForm) {
    // 薬マスタを登録or更新
    Map<Integer, Integer> medicineIdandSeqMap = new HashMap<>();

    StringBuffer sqlForMedicineUpdate = new StringBuffer();
    sqlForMedicineUpdate.append("UPDATE medicine SET ").append("medicine_name = :medicine_name, ")
        .append("updated_at = :updated_at ").append("WHERE medicine_id = :medicine_id ")
        .append("RETURNING medicine_id");

    StringBuffer sqlForMedicineInsert = new StringBuffer();
    sqlForMedicineInsert.append("INSERT INTO medicine ( ").append("medicine_name, ")
        .append("created_at, ").append("updated_at ").append(") VALUES ( ")
        .append(":medicine_name, ").append(":created_at, ").append(":updated_at ")
        .append(") RETURNING medicine_id ");

    // パラメータ
    noteForm.getMedicineList().stream().forEach(medicine -> {
      if (medicine.getMedicineId() != null) {
        // update
        SqlParameterSource paramsForMedicine =
            new MapSqlParameterSource().addValue("medicine_name", medicine.getMedicineName())
                .addValue("updated_at", new Timestamp(new Date().getTime()))
                .addValue("medicine_id", medicine.getMedicineId());
        medicineIdandSeqMap.put(
            jdbc.queryForObject(sqlForMedicineUpdate.toString(), paramsForMedicine, Integer.class),
            medicine.getMedicineSeq());
      } else {
        // insert
        SqlParameterSource paramsForMedicine =
            new MapSqlParameterSource().addValue("medicine_name", medicine.getMedicineName())
                .addValue("created_at", new Timestamp(new Date().getTime()))
                .addValue("updated_at", new Timestamp(new Date().getTime()));
        medicineIdandSeqMap.put(
            jdbc.queryForObject(sqlForMedicineInsert.toString(), paramsForMedicine, Integer.class),
            medicine.getMedicineSeq());
      }
    });

    return medicineIdandSeqMap;
  }

  private void upsertUsersNotes(NoteForm noteForm, Optional<Integer> noteId) {
    // ユーザー_痛み記録マスタを登録or更新
    StringBuffer sqlForUsersNotes = new StringBuffer();
    sqlForUsersNotes.append("INSERT INTO users_notes (fk_user_id, fk_note_id) ")
        .append("VALUES (:fk_user_id, :fk_note_id) ")
        .append("ON CONFLICT ON CONSTRAINT users_notes_pkey ").append("DO NOTHING");

    // ユーザー_痛み記録マスタのパラメータ設定
    SqlParameterSource paramForUserNotes = new MapSqlParameterSource()
        .addValue("fk_user_id", noteForm.getUserId()).addValue("fk_note_id", noteId.get());

    jdbc.update(sqlForUsersNotes.toString(), paramForUserNotes);
  }

  private Optional<Integer> updateNote(NoteForm noteForm) {
    // 痛み記録登録用SQL
    StringBuffer sqlForNotes = new StringBuffer();
    sqlForNotes.append("UPDATE notes SET ").append("pain_level = :pain_level, ")
        .append("memo = :memo, ").append("created_at = :created_at, ")
        .append("updated_at = :updated_at ").append("WHERE note_id = :note_id ")
        .append("RETURNING note_id");

    // パラメータ設定
    SqlParameterSource paramsForNotes = new MapSqlParameterSource()
        .addValue("pain_level", noteForm.getPainLevel()).addValue("memo", noteForm.getMemo())
        .addValue("created_at", new Timestamp(new Date().getTime()))
        .addValue("updated_at", new Timestamp(new Date().getTime()))
        .addValue("note_id", noteForm.getNoteId());

    Optional<Integer> noteId = Optional
        .ofNullable(jdbc.queryForObject(sqlForNotes.toString(), paramsForNotes, Integer.class));
    return noteId;
  }

}
