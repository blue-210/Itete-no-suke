package app.itetenosuke.domain.common.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.common.repository.ImageDao;
import app.itetenosuke.domain.note.model.NoteForm;

@Repository("ImageDaoNamedJdbcImpl")
public class ImageDaoNamedJdbcImpl implements ImageDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Override
	public List<Image> getImagesPath(NoteForm noteForm) {
		List<Image> imagePathList = new ArrayList<>();
		
		String sqlForImages = "SELECT i.* "
				+ " FROM users u INNER JOIN users_notes un ON u.user_id = un.fk_user_id"
				+ "              INNER JOIN notes n ON n.note_id = un.fk_note_id"
				+ "              INNER JOIN notes_images ni ON n.note_id = ni.fk_note_id"
				+ "              INNER JOIN images i ON i.images_id = ni.fk_images_id"
				+ " WHERE u.user_id = :user_id AND n.note_id = :note_id";
		
		SqlParameterSource paramForImages = new MapSqlParameterSource()
				.addValue("user_id", noteForm.getUserId())
				.addValue("note_id", noteForm.getNoteId());
		
		List<Map<String, Object>> imageMapList = jdbc.queryForList(sqlForImages, paramForImages);
		for(Map<String, Object> imageMap : imageMapList) {
			Image image = new Image();
			image.setImagePath((String)imageMap.get("images_path"));
			imagePathList.add(image);
		}
		return imagePathList;
	}

}
