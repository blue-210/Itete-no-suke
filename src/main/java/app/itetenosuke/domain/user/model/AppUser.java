package app.itetenosuke.domain.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class AppUser {
  private Long userId;
  private String userName;
  private String password;
  private String email;
  private int age;
  private Date birthday;
  private String role;
  private String status;
}
