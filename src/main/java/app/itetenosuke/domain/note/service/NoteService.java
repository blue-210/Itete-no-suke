package app.itetenosuke.domain.note.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.note.repository.NoteDao;
import app.itetenosuke.domain.painrecord.NoteForm;

@Transactional(rollbackFor = Exception.class)
@Service
public class NoteService {
  @Autowired
  @Qualifier("NoteDaoNamedJdbcImpl")
  NoteDao noteDao;

  public Integer createNote(NoteForm noteForm) {
    int noteId = noteDao.createNote(noteForm);
    return noteId;
  }

  public NoteForm getNote(long userId, long noteId) {
    return noteDao.getNote(userId, noteId);
  }

  public List<NoteForm> getNoteList(long userId) {
    return noteDao.getNoteList(userId);
  }


  public Integer editNote(NoteForm noteForm) {
    return noteDao.editNote(noteForm);
  }
}
