package app.itetenosuke.domain.note.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.repository.NoteDao;

@Transactional
@Service
public class NoteService {
	@Autowired
	@Qualifier("NoteDaoJdbcImpl")
	NoteDao noteDao;
	
	public int createNote(NoteForm noteForm) {
		int noteId = noteDao.createNote(noteForm);
		
		boolean result = false;
		if ( noteId > 0 ) {
			result = true;
		}
		
		return noteId;
	}
	
	public NoteForm getNote(long noteId) {
		return noteDao.getNote(noteId);
	}
	
	public List<NoteForm> getNoteList(long userId){
		return noteDao.getNoteList(userId);
	}
	
	
	public boolean editNote(NoteForm noteForm) {
		int rowNum = noteDao.editNote(noteForm);
		
		boolean result = false;
		if ( rowNum > 0) {
			result = true;
		}
		return result;
	}
}
