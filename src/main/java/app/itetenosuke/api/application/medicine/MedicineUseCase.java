package app.itetenosuke.api.application.medicine;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.medicine.IMedicineRepository;
import app.itetenosuke.api.domain.medicine.Medicine;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.infra.db.medicine.MedicineNotFoundException;
import app.itetenosuke.api.presentation.controller.shared.MedicineReqBody;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicineUseCase {
  private IMedicineRepository medicineRepository;

  @Transactional(readOnly = true)
  public List<MedicineDto> getMedicineList(String userId) {
    return medicineRepository
        .findAllByUserId(userId)
        .stream()
        .map(
            v -> {
              return MedicineDto.builder()
                  .medicineId(v.getMedicineId())
                  .medicineName(v.getMedicineName())
                  .status(v.getStatus())
                  .createdAt(v.getCreatedAt())
                  .updatedAt(v.getUpdatedAt())
                  .build();
            })
        .collect(Collectors.toList());
  }

  public String createMedicine(MedicineReqBody req) {
    Medicine medicine =
        Medicine.builder()
            .medicineId(req.getMedicineId())
            .medicineName(req.getMedicineName())
            .status(Status.ALIVE.toString())
            .createdAt(req.getCreatedAt())
            .updatedAt(req.getUpdatedAt())
            .build();
    medicineRepository.save(medicine);
    return medicine.getMedicineId();
  }

  public MedicineDto getMedicine(String medicineId) {
    return medicineRepository
        .findByMedicineId(medicineId)
        .map(
            v ->
                MedicineDto.builder()
                    .medicineId(v.getMedicineId())
                    .medicineName(v.getMedicineName())
                    .medicineMemo(v.getMedicineMemo())
                    .status(v.getStatus())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .orElseThrow(MedicineNotFoundException::new);
  }

  public String updateMedicine(MedicineReqBody req) {
    Medicine medicine =
        Medicine.builder()
            .medicineId(req.getMedicineId())
            .medicineName(req.getMedicineName())
            .status(Status.ALIVE.toString())
            .createdAt(req.getCreatedAt())
            .updatedAt(req.getUpdatedAt())
            .build();

    medicineRepository.save(medicine);
    return medicine.getMedicineId();
  }
}
