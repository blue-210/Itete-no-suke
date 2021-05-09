package app.itetenosuke.api.domain.user;

import java.util.Date;

import lombok.Data;

@Data
public class AppUser {
  private String userId;
  private String userName;
  private String password;
  private String email;
  private int age;
  private Date birthday;
  private String role;
  private String status;
}
