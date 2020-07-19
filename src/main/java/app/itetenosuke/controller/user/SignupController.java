package app.itetenosuke.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.itetenosuke.domain.user.model.SignupForm;
import app.itetenosuke.domain.user.service.UserService;

@Controller
public class SignupController {
  @Autowired
  private UserService userService;

  @GetMapping("/signup")
  public String showSignupPage(@ModelAttribute SignupForm signupForm, Model model) {
    model.addAttribute("contents", "login/signup :: signup_contents");
    return "home/homeLayout";
  }


  @PostMapping("/signup")
  public String createUser(@ModelAttribute @Validated SignupForm signupForm,
      BindingResult bindingResult, final HttpServletRequest request, Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("contents", "login/signup :: signup_contents");
      return "home/homeLayout";
    }

    String returnPath = "redirect:/home";
    if (!userService.createUser(signupForm, request)) {
      model.addAttribute("contents", "login/signup :: signup_contents");
      returnPath = "home/homeLayout";
    }
    return returnPath;
  }
}
