package app.itetenosuke.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import app.itetenosuke.domain.user.model.AppUser;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userService.selectOne(username);
    return new UserDetailsImpl(user);
  }

}
