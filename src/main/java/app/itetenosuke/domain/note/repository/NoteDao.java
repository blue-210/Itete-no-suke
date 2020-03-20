package app.itetenosuke.domain.note.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import app.itetenosuke.domain.note.model.NoteForm;

public interface NoteDao {
	public int createNote(NoteForm noteForm) throws DataAccessException;
	public NoteForm getNote(long noteId) throws DataAccessException;
	public List<NoteForm> getNoteList(long userId) throws DataAccessException;
	public int editNote(NoteForm noteForm) throws DataAccessException;
}
