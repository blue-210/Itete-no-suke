package app.itetenosuke.domain.user.repository.jdbc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.user.model.AppUser;
import app.itetenosuke.domain.user.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public int insertOne(AppUser user) throws DataAccessException {
		StringBuffer sqlForUserInsertOne = new StringBuffer();
		sqlForUserInsertOne.append("INSERT INTO users (")
						   .append(" password,")
						   .append(" user_name,")
						   .append(" email,")
						   .append(" birthday,")
						   .append(" age,")
						   .append(" role,")
						   .append(" status,")
						   .append(" created_at,")
						   .append(" updated_at")
						   .append(" ) VALUES (")
						   .append(" :password,")
						   .append(" :user_name,")
						   .append(" :email,")
						   .append(" :birthday,")
						   .append(" :age,")
						   .append(" :role,")
						   .append(" :status,")
						   .append(" :created_at,")
						   .append(" :updated_at")
						   .append(" ) ");
		
		SqlParameterSource paramForUserInsertOne = new MapSqlParameterSource()
				.addValue("password", passwordEncoder.encode(user.getPassword()))
				.addValue("user_name", user.getUserName())
				.addValue("email", user.getEmail())
				.addValue("birthday", user.getBirthday())
				.addValue("age", user.getAge())
				.addValue("role", user.getRole())
				.addValue("status", user.getStatus())
				.addValue("created_at", new Timestamp(new Date().getTime()))
				.addValue("updated_at", new Timestamp(new Date().getTime()));
		
		int result = jdbc.update(sqlForUserInsertOne.toString(), paramForUserInsertOne);
		return result;
	}

	@Override
	public AppUser selectOne(String email) throws DataAccessException {
		StringBuffer sqlForUserSelectOne = new StringBuffer();
		sqlForUserSelectOne.append("SELECT *")
						   .append(" FROM users")
						   .append(" WHERE email = :email");
		
		SqlParameterSource paramForUserSelectOne = new MapSqlParameterSource()
				.addValue("email", email);
		
		
		Map<String, Object> map = jdbc.queryForMap(sqlForUserSelectOne.toString(), paramForUserSelectOne);
		
		AppUser user = new AppUser();
		user.setUserId((Long)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setEmail((String)map.get("email"));
		user.setAge((Integer)map.get("age"));
		user.setBirthday((Date)map.get("birthday"));
		user.setRole((String)map.get("role"));
		user.setStatus((String)map.get("status"));
		
		return user;
	}
}
