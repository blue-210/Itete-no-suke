package app.itetenosuke.domain.bodyParts.repository.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.bodyParts.repository.BodyPartsDao;

@Repository("BodyPartsDaoNamedJdbcImpl")
public class BodyPartsDaoNamedJdbcImpl implements BodyPartsDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbc;

  @Override
  public List<BodyParts> getBodyPartsList(Long userId) {
    List<BodyParts> bodyPartsList = new ArrayList<BodyParts>();

    StringBuffer sqlForBodyPartsList = new StringBuffer();
    sqlForBodyPartsList.append("SELECT b.* ")
        .append("FROM users u INNER JOIN users_bodyparts ub ON u.user_id = ub.fk_user_id ")
        .append("             INNER JOIN bodyparts b ON b.body_parts_id = ub.fk_body_parts_id ")
        .append("WHERE u.user_id = :user_id ").append("AND b.status = 'ALIVE' ")
        .append("ORDER BY created_at DESC, updated_at DESC");

    SqlParameterSource paramForBodyPartsList =
        new MapSqlParameterSource().addValue("user_id", userId);

    List<Map<String, Object>> bodyPartsMapList =
        jdbc.queryForList(sqlForBodyPartsList.toString(), paramForBodyPartsList);
    for (Map<String, Object> bodyPartsMap : bodyPartsMapList) {
      BodyParts bodyParts = new BodyParts();
      bodyParts.setBodyPartsId((Long) bodyPartsMap.get("body_parts_id"));
      bodyParts.setBodyPartsName((String) bodyPartsMap.get("body_parts_name"));
      bodyParts.setCreatedAt(((Timestamp) bodyPartsMap.get("created_at")).toLocalDateTime());
      bodyParts.setCreatedAt(((Timestamp) bodyPartsMap.get("updated_at")).toLocalDateTime());
      bodyPartsList.add(bodyParts);
    }

    return bodyPartsList;
  }

  @Override
  public boolean addBodyParts(BodyParts bodyparts) {
    int bodyPartsId = insertBodyParts(bodyparts);
    bodyparts.setBodyPartsId(Long.valueOf(bodyPartsId));
    return insertUsersBodyParts(bodyparts);
  }

  private boolean insertUsersBodyParts(BodyParts bodyparts) {
    StringBuffer sqlForUsersBodyParts = new StringBuffer();
    sqlForUsersBodyParts
        .append("INSERT INTO users_bodyparts VALUES (:fk_user_id, :fk_body_parts_id)");

    SqlParameterSource paramForUsersBodyParts =
        new MapSqlParameterSource().addValue("fk_user_id", bodyparts.getUserId())
            .addValue("fk_body_parts_id", bodyparts.getBodyPartsId());

    return 0 < jdbc.update(sqlForUsersBodyParts.toString(), paramForUsersBodyParts);
  }

  private int insertBodyParts(BodyParts bodyparts) {
    StringBuffer sqlForAddBodyParts = new StringBuffer();
    sqlForAddBodyParts.append("INSERT INTO bodyparts (").append(" body_parts_name,")
        .append(" created_at,").append(" updated_at").append(" ) VALUES ( ")
        .append(":body_parts_name,").append(":created_at,").append(":updated_at")
        .append(") RETURNING body_parts_id");

    SqlParameterSource paramForAddBodyParts =
        new MapSqlParameterSource().addValue("body_parts_name", bodyparts.getBodyPartsName())
            .addValue("created_at", new Timestamp(new Date().getTime()))
            .addValue("updated_at", new Timestamp(new Date().getTime()));

    int bodyPartsId =
        jdbc.queryForObject(sqlForAddBodyParts.toString(), paramForAddBodyParts, Integer.class);
    return bodyPartsId;
  }

  @Override
  public boolean deleteBodyParts(BodyParts bodyparts) {
    StringBuffer sqlForDeleteBodyParts = new StringBuffer();
    sqlForDeleteBodyParts.append("UPDATE bodyparts ").append("SET status = 'DELETED' ")
        .append("FROM users_bodyparts ub INNER JOIN users u ON ub.fk_user_id = u.user_id ")
        .append("WHERE ub.fk_body_parts_id = body_parts_id ").append("AND u.user_id = :user_id ")
        .append("AND bodyparts.body_parts_id = :body_parts_id ");

    SqlParameterSource paramForDeleteBodyParts =
        new MapSqlParameterSource().addValue("user_id", bodyparts.getUserId())
            .addValue("body_parts_id", bodyparts.getBodyPartsId());

    return 0 < jdbc.update(sqlForDeleteBodyParts.toString(), paramForDeleteBodyParts);
  }

  @Override
  public boolean editBodyParts(BodyParts bodyparts) {
    StringBuffer sqlForDeleteBodyParts = new StringBuffer();
    sqlForDeleteBodyParts.append("UPDATE bodyparts ")
        .append("SET body_parts_name = :body_parts_name ")
        .append("FROM users_bodyparts ub INNER JOIN users u ON ub.fk_user_id = u.user_id ")
        .append("WHERE ub.fk_body_parts_id = body_parts_id ").append("AND u.user_id = :user_id ")
        .append("AND body_parts_id = :body_parts_id ").append("AND bodyparts.status = 'ALIVE'");

    SqlParameterSource paramForDeleteBodyParts =
        new MapSqlParameterSource().addValue("body_parts_name", bodyparts.getBodyPartsName())
            .addValue("user_id", bodyparts.getUserId())
            .addValue("body_parts_id", bodyparts.getBodyPartsId());

    return 0 < jdbc.update(sqlForDeleteBodyParts.toString(), paramForDeleteBodyParts);
  }
}
