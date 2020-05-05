package app.itetenosuke.domain.user.repository;

import org.springframework.dao.DataAccessException;

import app.itetenosuke.domain.user.model.AppUser;

public interface UserDao {
	public int insertOne(AppUser AppUser) throws DataAccessException;
	public AppUser selectOne(String email) throws DataAccessException;
}
