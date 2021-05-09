package app.itetenosuke.api.application.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.user.AppUser;
import app.itetenosuke.api.domain.user.UserDetailsImpl;
import app.itetenosuke.api.infra.db.user.UserDaoJdbcImpl;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserDaoJdbcImpl userDao;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userDao.selectOne(username);
    return new UserDetailsImpl(user);
  }
}
