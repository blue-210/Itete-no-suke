package app.itetenosuke.domain.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {
	private AppUser user;
	
	public UserDetailsImpl(AppUser user) {
		super(user.getEmail(), user.getPassword(),AuthorityUtils.createAuthorityList(user.getRole()));
		this.user = user;
	}

	// ユーザー情報取得
	public Long getUserId() {
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
		return this.user.isStatus();
	}

}
