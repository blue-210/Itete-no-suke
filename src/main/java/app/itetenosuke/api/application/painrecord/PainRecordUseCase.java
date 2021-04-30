package app.itetenosuke.api.application.painrecord;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.bodypart.IBodyPartRepository;
import app.itetenosuke.api.domain.medicine.IMedicineRepository;
import app.itetenosuke.api.domain.medicine.Medicine;
import app.itetenosuke.api.domain.painrecord.IPainRecordRepository;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.api.infra.db.painrecord.PainRecordNotFoundException;
import app.itetenosuke.api.presentation.controller.painrecord.PainRecordReqBody;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PainRecordUseCase {
  private final IPainRecordRepository painRecordRepository;
  private final IMedicineRepository medicineRepository;
  private final IBodyPartRepository bodyPartRepository;

  @Transactional(readOnly = true)
  public PainRecordDto getPainRecord(String painRecordID) {
    Optional<PainRecord> painRecord = painRecordRepository.findById(painRecordID);
    List<Medicine> medicineList = medicineRepository.findAllByPainRecordId(painRecordID);
    return painRecord
        .map(
            v ->
                PainRecordDto.builder()
                    .painRecordId(v.getPainRecordId())
                    .painLevel(v.getPainLevel())
                    .medicineList(medicineList)
                    .memo(v.getMemo())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .orElseThrow(PainRecordNotFoundException::new);
  }

  public boolean updatePainRecord(PainRecordReqBody req) {
    PainRecord painRecord =
        PainRecord.builder()
            .painRecordId(req.getPainRecordId())
            .painLevel(req.getPainLevel())
            .medicineList(req.getMedicineList())
            .bodyPartsList(req.getBodyPartsList())
            .memo(req.getMemo())
            .updatedAt(req.getUpdatedAt())
            .build();

    boolean canUpdatePainRecord = painRecordRepository.updatePainRecord(painRecord);
    //
    // 登録されてない薬がある場合は薬を新しく登録する
    List<Medicine> insertMedicineList =
        painRecord
            .getMedicineList()
            .stream()
            // existsはリポジトリで実装しない？？
            .filter(medicine -> !medicineRepository.exists(medicine, painRecord.getPainRecordId()))
            .collect(Collectors.toList());

    boolean canInsertMedicine = true;
    if (!insertMedicineList.isEmpty()) {
      // TODO ファクトリに切り出し?
      PainRecord painRecordWithInsertMedicine =
          PainRecord.builder()
              .painRecordId(req.getPainRecordId())
              .painLevel(req.getPainLevel())
              .medicineList(insertMedicineList)
              .memo(req.getMemo())
              .updatedAt(req.getUpdatedAt())
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
          PainRecord.builder()
              .painRecordId(req.getPainRecordId())
              .painLevel(req.getPainLevel())
              .medicineList(updateMedicineList)
              .memo(req.getMemo())
              .updatedAt(req.getUpdatedAt())
              .build();
      canUpdateMedicine = medicineRepository.updateMedicineRecords(painRecordWithUpdateMedicine);
    }

    bodyPartRepository.save(painRecord);

    return canUpdatePainRecord && (canUpdateMedicine && canInsertMedicine);
  }

  @Transactional
  public boolean createPainRecord(PainRecordReqBody req) {
    PainRecord painRecord =
        PainRecord.builder()
            .painRecordId(req.getPainRecordId())
            .painLevel(req.getPainLevel())
            .medicineList(req.getMedicineList())
            .memo(req.getMemo())
            .updatedAt(req.getUpdatedAt())
            .createdAt(req.getCreatedAt())
            .build();
    return painRecordRepository.createPainRecord(painRecord);
  }
}
