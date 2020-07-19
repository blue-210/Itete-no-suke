package app.itetenosuke.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.itetenosuke.domain.note.service.NoteService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class HomeController {

  @Autowired
  NoteService noteService;

  @GetMapping("/home")
  public String getHome(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    model.addAttribute("contents", "home/home :: home_contents");

    return "home/homeLayout";
  }
}
