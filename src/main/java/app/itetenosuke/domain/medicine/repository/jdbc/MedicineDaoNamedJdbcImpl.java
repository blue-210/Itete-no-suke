package app.itetenosuke.domain.medicine.repository.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.medicine.repository.MedicineDao;

@Repository("MedicineDaoNamedJdbcImpl")
public class MedicineDaoNamedJdbcImpl implements MedicineDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Override
	public List<Medicine> getMedicineList(long userId) throws DataAccessException {
		List<Medicine> medicineList = new ArrayList<Medicine>();
		
		StringBuffer sqlForMedicineList = new StringBuffer();
		sqlForMedicineList.append("SELECT m.*")
						  .append(" FROM users u INNER JOIN users_medicine um ON u.user_id = um.fk_user_id")
						  .append("              INNER JOIN medicine m ON m.medicine_id = um.fk_medicine_id")
						  .append(" WHERE u.user_id = :user_id")
						  .append(" AND m.status = 'ALIVE' ")
						  .append(" ORDER BY created_at DESC, updated_at DESC");
		
		SqlParameterSource paramForMedicineList = new MapSqlParameterSource().addValue("user_id", userId);
		
		List<Map<String, Object>> medicineMapList = jdbc.queryForList(sqlForMedicineList.toString(), paramForMedicineList);
		for(Map<String, Object> medicineMap : medicineMapList) {
			Medicine medicine = new Medicine();
			medicine.setMedicineId((Long)medicineMap.get("medicine_id"));
			medicine.setMedicineSeq((Integer)medicineMap.get("medicine_seq"));
			medicine.setMedicineName((String)medicineMap.get("medicine_name"));
			medicine.setMedicineMemo((String)medicineMap.getOrDefault("medicine_mem", ""));
			medicine.setUpdatedAt(((Timestamp)medicineMap.get("updated_at")).toLocalDateTime());
			medicine.setCreatedAt(((Timestamp)medicineMap.get("created_at")).toLocalDateTime());
			medicineList.add(medicine);
		}
		return medicineList;
	}

	@Override
	public boolean addMedicine(Medicine medicine) {
		boolean result = false;
		
		Long medicineId = insertMedicine(medicine);
		medicine.setMedicineId(medicineId);
		
		result = insertUsersMedicine(medicine);
		
		return result;
	}

	private boolean insertUsersMedicine(Medicine medicine) {
		StringBuffer sqlForUsersMedicine = new StringBuffer();
		sqlForUsersMedicine.append("INSERT INTO users_medicine VALUES (:fk_user_id, :fk_medicine_id)");
		
		SqlParameterSource paramForUsersMedicine = new MapSqlParameterSource()
				.addValue("fk_user_id", medicine.getUserId())
				.addValue("fk_medicine_id", medicine.getMedicineId());
		
		return 0 < jdbc.update(sqlForUsersMedicine.toString(), paramForUsersMedicine);
	}

	private Long insertMedicine(Medicine medicine) {
		StringBuffer sqlForAddMedicine = new StringBuffer();
		sqlForAddMedicine.append("INSERT INTO medicine ( ")
						 .append("   medicine_name,")
						 .append("   updated_at,")
						 .append("   created_at")
						 .append(") VALUES (")
						 .append(" :medicine_name,")
						 .append(" :updated_at,")
						 .append(" :created_at")
						 .append(") RETURNING medicine_id");
		
		SqlParameterSource paramForAddMedicine = new MapSqlParameterSource()
				.addValue("medicine_name", medicine.getMedicineName())
				.addValue("updated_at", new Timestamp(new Date().getTime()))
				.addValue("created_at", new Timestamp(new Date().getTime()));
		
		return jdbc.queryForObject(sqlForAddMedicine.toString(), paramForAddMedicine, Long.class);
	}

	@Override
	public boolean deleteMedicine(Medicine medicine) {
		StringBuffer sqlForDeleteMedicine = new StringBuffer();
		sqlForDeleteMedicine.append("UPDATE medicine ")
							.append("SET status = 'DELETED' ")
							.append("FROM users_medicine um INNER JOIN users u ON um.fk_user_id = u.user_id ")
							.append("WHERE um.fk_medicine_id = medicine_id ")
							.append("AND u.user_id = :user_id ")
							.append("AND medicine_id = :medicine_id ");
		
		SqlParameterSource paramForDeleteMedicine = new MapSqlParameterSource()
				.addValue("medicine_name", medicine.getMedicineName())
				.addValue("user_id", medicine.getUserId())
				.addValue("medicine_id", medicine.getMedicineId());
		
		return 0 < jdbc.update(sqlForDeleteMedicine.toString(), paramForDeleteMedicine);
	}
	
	@Override
	public boolean editMedicine(Medicine medicine) {
		StringBuffer sqlForDeleteMedicine = new StringBuffer();
		
		sqlForDeleteMedicine.append("UPDATE medicine ")
		.append("SET medicine_name = :medicine_name ")
		.append("FROM users_medicine um INNER JOIN users u ON um.fk_user_id = u.user_id ")
		.append("WHERE um.fk_medicine_id = medicine_id ")
		.append("AND u.user_id = :user_id ")
		.append("AND medicine_id = :medicine_id ")
		.append("AND medicine.status = 'ALIVE'");
		
		SqlParameterSource paramForDeleteMedicine = new MapSqlParameterSource()
				.addValue("medicine_name", medicine.getMedicineName())
				.addValue("user_id", medicine.getUserId())
				.addValue("medicine_id", medicine.getMedicineId());
		
		return 0 < jdbc.update(sqlForDeleteMedicine.toString(), paramForDeleteMedicine);
	}

}
