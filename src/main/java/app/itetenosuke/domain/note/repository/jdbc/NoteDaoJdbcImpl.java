package app.itetenosuke.domain.note.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.repository.NoteDao;

@Repository("NoteDaoJdbcImpl")
public class NoteDaoJdbcImpl implements NoteDao {
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int createNote(NoteForm noteForm) throws DataAccessException {
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		
		// 痛み記録に登録
		Integer noteId = jdbc.queryForObject("INSERT INTO notes ("
				+ "memo"
				+ ", pain_level"
				+ ", medicine1"
				+ ", medicine2"
				+ ", medicine3"
				+ ", medicine4"
				+ ", medicine5"
				+ ", part1"
				+ ", part2"
				+ ", part3"
				+ ", part4"
				+ ", part5"
				+ ", part6"
				+ ", part7"
				+ ", part8"
				+ ", part9"
				+ ", part10"
				+ ", created_at"
				+ ") VALUES ("
				+ "  ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ", ?"
				+ ") RETURNING note_id"
				, Integer.class
				, noteForm.getMemo()
				, "1"
				, "薬テスト1"
				, "薬テスト2"
				, "薬テスト3"
				, "薬テスト4"
				, "薬テスト5"
				, "部位テスト1"
				, "部位テスト2"
				, "部位テスト3"
				, "部位テスト4"
				, "部位テスト5"
				, "部位テスト6"
				, "部位テスト7"
				, "部位テスト8"
				, "部位テスト9"
				, "部位テスト10"
				, timeStamp);
		
		// ユーザ痛み記録関連テーブルに登録
		int rowNum = jdbc.update("INSERT INTO users_notes(fk_user_id, fk_note_id) VALUES (?, ?)", noteForm.getUserId(), noteId);
		
		return noteId.intValue();
	}

	@Override
	public NoteForm getNote(long noteId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT *"
				+ " FROM notes"
				+ " WHERE note_id = ?"
				, noteId);
		
		NoteForm noteForm = new NoteForm();
		noteForm.setNoteId((Long)map.get("note_id"));
		noteForm.setMemo((String)map.get("memo"));
		
		return noteForm;
	}

	@Override
	public List<NoteForm> getNoteList(long userId) throws DataAccessException {
		List<Map<String, Object>> list = jdbc.queryForList("SELECT n.*"
				+ " FROM notes n "
				+ " INNER JOIN users_notes un ON n.note_id = un.fk_note_id"
				+ " INNER JOIN users u ON u.user_id = un.fk_user_id"
				+ " WHERE u.user_id = ?"
				, userId);
		
		List<NoteForm> noteList = new ArrayList<>();
		for(Map<String, Object> map : list) {
			NoteForm note = new NoteForm();
			note.setNoteId((Long)map.get("note_id"));
			LocalDateTime createdDate = ((Timestamp)map.get("created_at")).toLocalDateTime();
			LocalDateTime updatedDate = ((Timestamp)map.get("updated_at")).toLocalDateTime();
			note.setCreatedAt(createdDate);
			note.setUpdatedAt(updatedDate);
			noteList.add(note);
		}
		return noteList;
	}
	
	@Override
	public int editNote(NoteForm noteForm) throws DataAccessException {
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());

		int rowNum = jdbc.update("UPDATE notes"
				+ " SET "
				+ "  pain_level = ?"
				+ ", medicine1 = ?"
				+ ", medicine2 = ?"
				+ ", medicine3 = ?"
				+ ", medicine4 = ?"
				+ ", medicine5 = ?"
				+ ", part1 = ?"
				+ ", part2 = ?"
				+ ", part3 = ?"
				+ ", part4 = ?"
				+ ", part5 = ?"
				+ ", part6 = ?"
				+ ", part7 = ?"
				+ ", part8 = ?"
				+ ", part9 = ?"
				+ ", part10 = ?"
				+ ", memo = ?"
				+ ", updated_at = ?"
				, "1"
				, "薬テスト1"
				, "薬テスト2"
				, "薬テスト3"
				, "薬テスト4"
				, "薬テスト5"
				, "部位テスト1"
				, "部位テスト2"
				, "部位テスト3"
				, "部位テスト4"
				, "部位テスト5"
				, "部位テスト6"
				, "部位テスト7"
				, "部位テスト8"
				, "部位テスト9"
				, "部位テスト10"
				, noteForm.getMemo()
				, timeStamp);
		return rowNum;
	}
}
