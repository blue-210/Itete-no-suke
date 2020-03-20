package app.itetenosuke.domain.user.repository.jdbc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.user.model.AppUser;
import app.itetenosuke.domain.user.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public int count() throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertOne(AppUser user) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AppUser selectOne(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT *"
				+ " FROM users"
				+ " WHERE email = ?"
				, userId);
		
		AppUser user = new AppUser();
		user.setUserId((Long)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setEmail((String)map.get("email"));
		user.setAge((Integer)map.get("age"));
		user.setBirthday((Date)map.get("birthday"));
		user.setRole((String)map.get("role"));
		user.setStatus((Boolean)map.get("status"));
		
		return user;
	}

	@Override
	public List<AppUser> selectMany() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateOne(AppUser user) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		// TODO Auto-generated method stub

	}

}
