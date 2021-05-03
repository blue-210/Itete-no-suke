package app.itetenosuke.domain.user.repository.jdbc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.user.model.AppUser;
import app.itetenosuke.domain.user.model.SignupForm;
import app.itetenosuke.domain.user.model.UserRole;
import app.itetenosuke.domain.user.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

  private final NamedParameterJdbcTemplate jdbc;

  private final PasswordEncoder passwordEncoder;

  public UserDaoJdbcImpl(NamedParameterJdbcTemplate jdbc, PasswordEncoder passwordEncoder) {
    this.jdbc = jdbc;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public int insertOne(SignupForm form) throws DataAccessException {
    StringBuffer sqlForUserInsertOne = new StringBuffer();
    sqlForUserInsertOne
        .append("INSERT INTO users (")
        .append(" password,")
        .append(" user_name,")
        .append(" email,")
        .append(" role,")
        .append(" status,")
        .append(" created_at,")
        .append(" updated_at")
        .append(" ) VALUES (")
        .append(" :password,")
        .append(" :user_name,")
        .append(" :email,")
        .append(" :role,")
        .append(" :status,")
        .append(" :created_at,")
        .append(" :updated_at")
        .append(" ) ");

    SqlParameterSource paramForUserInsertOne =
        new MapSqlParameterSource()
            .addValue("password", passwordEncoder.encode(form.getPassword()))
            .addValue("user_name", form.getUserName())
            .addValue("email", form.getEmail())
            .addValue("role", UserRole.ROLE_GENERAL.toString())
            .addValue("status", "ALIVE")
            .addValue("created_at", new Timestamp(new Date().getTime()))
            .addValue("updated_at", new Timestamp(new Date().getTime()));

    int result = jdbc.update(sqlForUserInsertOne.toString(), paramForUserInsertOne);
    return result;
  }

  @Override
  public AppUser selectOne(String email) throws DataAccessException, UsernameNotFoundException {
    StringBuffer sqlForUserSelectOne = new StringBuffer();
    sqlForUserSelectOne.append("SELECT *").append(" FROM users").append(" WHERE email = :email");

    SqlParameterSource paramForUserSelectOne = new MapSqlParameterSource().addValue("email", email);

    AppUser user = new AppUser();
    try {
      Map<String, Object> map =
          jdbc.queryForMap(sqlForUserSelectOne.toString(), paramForUserSelectOne);
      user.setUserId((String) map.get("user_id"));
      user.setPassword((String) map.get("password"));
      user.setUserName((String) map.get("user_name"));
      user.setEmail((String) map.get("email"));
      user.setRole((String) map.get("role"));
      user.setStatus((String) map.get("status"));
    } catch (DataAccessException de) {
      throw new UsernameNotFoundException("User do not found.");
    }

    return user;
  }

  @Override
  public boolean exists(String email) {
    boolean result = false;

    StringBuffer sqlForUserExists = new StringBuffer();
    sqlForUserExists.append("SELECT *").append(" FROM users").append(" WHERE email = :email");

    SqlParameterSource paramForUserExists = new MapSqlParameterSource().addValue("email", email);

    List<Map<String, Object>> resultList =
        jdbc.queryForList(sqlForUserExists.toString(), paramForUserExists);

    if (!resultList.isEmpty()) {
      result = true;
    }

    return result;
  }
}
