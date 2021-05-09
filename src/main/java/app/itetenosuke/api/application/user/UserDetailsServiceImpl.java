package app.itetenosuke.api.application.user;

import java.util.OptionalInt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.user.AppUser;
import app.itetenosuke.api.domain.user.SignupForm;
import app.itetenosuke.api.domain.user.UserDao;
import app.itetenosuke.api.domain.user.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserDao userDao;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userDao.selectOne(username);
    return new UserDetailsImpl(user);
  }

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
