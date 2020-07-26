package app.itetenosuke.controller.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class LoginController {
  @GetMapping("/login")
  public String getLogin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    if (userDetails != null) {
      model.addAttribute("contents", "home/home :: home_contents");
    } else {
      model.addAttribute("contents", "login/login :: login_contents");
    }
    return "home/homeLayout";
  }
}
