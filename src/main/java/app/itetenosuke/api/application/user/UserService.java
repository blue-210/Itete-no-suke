package app.itetenosuke.api.application.user;

import java.util.OptionalInt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.user.AppUser;
import app.itetenosuke.api.domain.user.SignupForm;
import app.itetenosuke.api.domain.user.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
@AllArgsConstructor
public class UserService {
  private final UserDao userDao;
  private final AuthenticationManager authenticationManager;

  public boolean createUser(SignupForm form, final HttpServletRequest request) {
    boolean canCreated = false;

    // すでに登録されていないかチェックする
    if (userDao.exists(form.getEmail())) {
      log.info("User already exists.");
      return canCreated;
    }

    OptionalInt insertResultOpt = OptionalInt.of(userDao.insertOne(form));

    if (insertResultOpt.isPresent()) {
      try {
        request.login(form.getEmail(), form.getPassword());
      } catch (ServletException se) {
        log.error("Authentication Failed");
      }
      canCreated = true;
    }
    return canCreated;
  }

  public AppUser selectOne(String userName) {
    return userDao.selectOne(userName);
  }
}
