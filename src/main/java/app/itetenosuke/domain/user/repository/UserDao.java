package app.itetenosuke.domain.user.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import app.itetenosuke.domain.user.model.AppUser;

public interface UserDao {
	
	public int count() throws DataAccessException;
	
	public int insertOne(AppUser AppUser) throws DataAccessException;
	
	public AppUser selectOne(String AppUserId) throws DataAccessException;
	
	public List<AppUser> selectMany() throws DataAccessException;
	
	public int updateOne(AppUser AppUser) throws DataAccessException;
	
	public int deleteOne(String AppUserId) throws DataAccessException;
	
	public void userCsvOut() throws DataAccessException;
}
