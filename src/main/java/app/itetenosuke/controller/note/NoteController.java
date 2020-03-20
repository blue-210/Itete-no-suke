package app.itetenosuke.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.service.NoteService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class NoteController {
	@Autowired
	NoteService noteService;
	
	@GetMapping("/note/show/{noteId}")
	public String showNote(@PathVariable("noteId") String noteId
			, @AuthenticationPrincipal UserDetailsImpl userDetails
			, Model model) {
		// 痛み記録を取得する
		NoteForm note = noteService.getNote(Long.valueOf(noteId));
		
		model.addAttribute("note", note);
		model.addAttribute("contents", "note/note :: note_contents");
		
		return "home/homeLayout";
	}
	
	@PostMapping(value = "/note", params = "edit")
	public String editNote(@AuthenticationPrincipal UserDetailsImpl userDetails
			, @ModelAttribute NoteForm noteForm
			, Model model) {
		// 痛み記録を編集する
		
		boolean result = noteService.editNote(noteForm);
		
		// 編集の成否によってメッセージを変える
		// TODO メッセージ取得をプロパティからにする
		if(result) {
			model.addAttribute("resultMessage", "編集が完了しました。");
		} else {
			model.addAttribute("resultMessage", "編集に失敗しました。再度お試しください");
		}
		model.addAttribute("contents", "note/note :: note_contents");
		
		return "redirect:/note/show/" + noteForm.getNoteId();
	}
	
	@GetMapping("/note/add")
	public String addNote(Model model) {
		model.addAttribute("contents", "note/note :: note_contents");
		return "home/homeLayout";
	}
	
	@PostMapping(value = "/note", params="create")
	public String postNoteCreate(@ModelAttribute NoteForm form
			, @AuthenticationPrincipal UserDetailsImpl userDetails
			, Model model) {
		
		NoteForm note = new NoteForm();
		note.setMemo(form.getMemo());
		note.setUserId(userDetails.getUserId());
		Integer noteId = noteService.createNote(note);

		// 作成の成否によってメッセージを変える
		// TODO メッセージ取得をプロパティからにする
		if(noteId != null) {
			model.addAttribute("resultMessage", "編集が完了しました。");
		} else {
			model.addAttribute("resultMessage", "編集に失敗しました。再度お試しください");
		}
		
		return "redirect:/note/show/" + noteId.toString() ;
	}
}
