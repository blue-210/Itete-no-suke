package app.itetenosuke.domain.common.repository;

import java.util.List;

import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.painrecord.NoteForm;

public interface ImageDao {
  public List<Image> getImagesPath(NoteForm noteForm);
}
