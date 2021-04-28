package app.itetenosuke.application.painrecord;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.medicine.IMedicineRepository;
import app.itetenosuke.domain.medicine.Medicine;
import app.itetenosuke.domain.painrecord.IPainRecordRepository;
import app.itetenosuke.domain.painrecord.PainRecord;
import app.itetenosuke.infra.db.painrecord.PainRecordNotFoundException;
import app.itetenosuke.presentation.controller.painrecord.PainRecordReqBody;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PainRecordUseCase {
  private final IPainRecordRepository painRecordRepository;
  private final IMedicineRepository medicineRepository;

  @Transactional(readOnly = true)
  public PainRecordDto getPainRecord(String painRecordID) {
    Optional<PainRecord> painRecord = painRecordRepository.findById(painRecordID);
    return painRecord.map(v -> new PainRecordDto(v)).orElseThrow(PainRecordNotFoundException::new);
  }

  public boolean updatePainRecord(PainRecordReqBody req) {
    PainRecord painRecord =
        new PainRecord.Builder()
            .setPainRecordId(req.getPainRecordId())
            .setUserId(req.getUserId())
            .setPainLevel(req.getPainLevel())
            .setMedicineList(req.getMedicineList())
            .setMemo(req.getMemo())
            .setUpdatedAt(req.getUpdatedAt())
            .build();

    boolean canUpdatePainRecord = painRecordRepository.updatePainRecord(painRecord);
    //
    // 登録されてない薬がある場合は薬を新しく登録する
    List<Medicine> insertMedicineList =
        painRecord
            .getMedicineList()
            .stream()
            .filter(medicine -> !medicineRepository.exists(medicine, painRecord.getPainRecordId()))
            .collect(Collectors.toList());

    boolean canInsertMedicine = true;
    if (!insertMedicineList.isEmpty()) {
      // TODO ファクトリに切り出し?
      PainRecord painRecordWithInsertMedicine =
          new PainRecord.Builder()
              .setPainRecordId(req.getPainRecordId())
              .setUserId(req.getUserId())
              .setPainLevel(req.getPainLevel())
              .setMedicineList(insertMedicineList)
              .setMemo(req.getMemo())
              .setUpdatedAt(req.getUpdatedAt())
              .build();

      canInsertMedicine = medicineRepository.createMedicineRecords(painRecordWithInsertMedicine);
    }

    // すでに登録された薬がある場合は薬を更新する
    List<Medicine> updateMedicineList =
        painRecord
            .getMedicineList()
            .stream()
            .filter(medicine -> medicineRepository.exists(medicine, painRecord.getPainRecordId()))
            .collect(Collectors.toList());

    boolean canUpdateMedicine = true;
    if (!updateMedicineList.isEmpty()) {
      // TODO ファクトリに切り出し?
      PainRecord painRecordWithUpdateMedicine =
          new PainRecord.Builder()
              .setPainRecordId(req.getPainRecordId())
              .setUserId(req.getUserId())
              .setPainLevel(req.getPainLevel())
              .setMedicineList(updateMedicineList)
              .setMemo(req.getMemo())
              .setUpdatedAt(req.getUpdatedAt())
              .build();
      canUpdateMedicine = medicineRepository.updateMedicineRecords(painRecordWithUpdateMedicine);
    }

    return canUpdatePainRecord && (canUpdateMedicine && canInsertMedicine);
  }

  @Transactional
  public boolean createPainRecord(PainRecordReqBody req) {
    PainRecord painRecord =
        new PainRecord.Builder()
            .setPainRecordId(req.getPainRecordId())
            .setUserId(req.getUserId())
            .setPainLevel(req.getPainLevel())
            .setMemo(req.getMemo())
            .setCreatedAt(req.getCreatedAt())
            .setUpdatedAt(req.getUpdatedAt())
            .build();
    return painRecordRepository.createPainRecord(painRecord);
  }
}
