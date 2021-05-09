package app.itetenosuke.api.domain.user;

import org.springframework.dao.DataAccessException;

public interface UserDao {
  public int insertOne(SignupForm form) throws DataAccessException;

  public AppUser selectOne(String email) throws DataAccessException;

  public boolean exists(String email) throws DataAccessException;
}
