package app.itetenosuke.domain.medicine.repository;

import java.util.List;

import app.itetenosuke.domain.medicine.model.Medicine;

public interface MedicineDao {
	public List<Medicine> getMedicineList(long userId);
	public boolean addMedicine(Medicine medicine);
	public boolean deleteMedicine(Medicine medicine);
	public boolean editMedicine(Medicine medicine);
}
