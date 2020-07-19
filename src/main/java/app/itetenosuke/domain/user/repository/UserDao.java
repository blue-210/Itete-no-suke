package app.itetenosuke.domain.user.repository;

import org.springframework.dao.DataAccessException;

import app.itetenosuke.domain.user.model.AppUser;
import app.itetenosuke.domain.user.model.SignupForm;

public interface UserDao {
  public int insertOne(SignupForm form) throws DataAccessException;

  public AppUser selectOne(String email) throws DataAccessException;

  public boolean exists(String email) throws DataAccessException;
}
