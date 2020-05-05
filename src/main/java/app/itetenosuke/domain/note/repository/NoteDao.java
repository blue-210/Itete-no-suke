package app.itetenosuke.domain.note.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import app.itetenosuke.domain.note.model.NoteForm;

public interface NoteDao {
	public Integer createNote(NoteForm noteForm) throws DataAccessException;
	public NoteForm getNote(long userId, long noteId) throws DataAccessException;
	public List<NoteForm> getNoteList(long userId) throws DataAccessException;
	public Integer editNote(NoteForm noteForm) throws DataAccessException;
}
