package app.itetenosuke.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.service.NoteService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class ImagesController {
  @Autowired
  private NoteService noteService;

  @GetMapping("/image/imagelist")
  public String showImageList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

    List<NoteForm> noteList = noteService.getNoteList(userDetails.getUserId());

    model.addAttribute("imageList", noteList);
    model.addAttribute("contents", "images/imageList :: image_list_contents");
    return "home/homeLayout";
  }

  @GetMapping("/image/detail/{noteId}")
  public String showImageDetail(@PathVariable("noteId") Long noteId,
      @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

    NoteForm note = noteService.getNote(userDetails.getUserId(), noteId);

    model.addAttribute("images", note);
    model.addAttribute("contents", "images/image :: image_contents");

    return "home/homeLayout";
  }
}
