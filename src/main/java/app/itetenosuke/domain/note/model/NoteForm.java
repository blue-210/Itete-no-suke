package app.itetenosuke.domain.note.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NoteForm {
	private Long noteId;
	private Long userId;
	// メモ
	@Length(min = 0, max = 250)
	private String memo;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
