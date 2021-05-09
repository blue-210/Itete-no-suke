package app.itetenosuke.api.domain.user;

import java.util.Date;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {
  /** */
  private static final long serialVersionUID = 245907654695993450L;

  private AppUser user;

  public UserDetailsImpl(AppUser user) {
    super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
    this.user = user;
  }

  // ユーザー情報取得
  public String getUserId() {
    return this.user.getUserId();
  }

  @Override
  public String getUsername() {
    return this.user.getUserName();
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  public String getEmail() {
    return this.user.getEmail();
  }

  public int getAge() {
    return this.user.getAge();
  }

  public Date getBirthday() {
    return this.user.getBirthday();
  }

  public String getRole() {
    return this.user.getRole();
  }

  public String getStatus() {
    return this.user.getStatus();
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    return "ALIVE".equals(this.user.getStatus());
  }
}
