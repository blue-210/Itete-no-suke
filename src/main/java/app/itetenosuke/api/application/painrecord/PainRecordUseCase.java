package app.itetenosuke.api.application.painrecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.bodypart.BodyPart;
import app.itetenosuke.api.domain.bodypart.IBodyPartRepository;
import app.itetenosuke.api.domain.image.IImageRepository;
import app.itetenosuke.api.domain.image.Image;
import app.itetenosuke.api.domain.medicine.IMedicineRepository;
import app.itetenosuke.api.domain.medicine.Medicine;
import app.itetenosuke.api.domain.painrecord.IPainRecordRepository;
import app.itetenosuke.api.domain.painrecord.PainRecord;
import app.itetenosuke.api.infra.db.painrecord.PainRecordNotFoundException;
import app.itetenosuke.api.presentation.controller.shared.PainRecordReqBody;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PainRecordUseCase {
  private final IPainRecordRepository painRecordRepository;
  private final IMedicineRepository medicineRepository;
  private final IBodyPartRepository bodyPartRepository;
  private final Map<String, IImageRepository> imageRepositoryMap;

  @Transactional(readOnly = true)
  public PainRecordDto getPainRecord(String painRecordID) {
    Optional<PainRecord> painRecord = painRecordRepository.findById(painRecordID);
    List<Medicine> medicineList = medicineRepository.findAllByPainRecordId(painRecordID);
    List<BodyPart> bodyPartList = bodyPartRepository.findAllByPainRecordId(painRecordID);
    List<Image> imageList =
        imageRepositoryMap.get("ImageRepositoryImpl").findAllByPainRecordId(painRecordID);
    return painRecord
        .map(
            v ->
                PainRecordDto.builder()
                    .painRecordId(v.getPainRecordId())
                    .userId(v.getUserId())
                    .painLevel(v.getPainLevel())
                    .medicineList(medicineList)
                    .bodyPartList(bodyPartList)
                    .imageList(imageList)
                    .memo(v.getMemo())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .orElseThrow(PainRecordNotFoundException::new);
  }

  public void createPainRecord(PainRecordReqBody req) {
    savePainRecord(req);
  }

  public void updatePainRecord(PainRecordReqBody req) {
    savePainRecord(req);
  }

  private void savePainRecord(PainRecordReqBody req) {
    List<Medicine> medicines =
        req.getMedicineList()
            .stream()
            .map(
                medicine ->
                    Medicine.builder()
                        .medicineId(medicine.getMedicineId())
                        .userId(medicine.getUserId())
                        .medicineSeq(medicine.getMedicineSeq())
                        .medicineName(medicine.getMedicineName())
                        .medicineMemo(medicine.getMedicineMemo())
                        .status(medicine.getStatus())
                        .createdAt(medicine.getCreatedAt())
                        .updatedAt(medicine.getUpdatedAt())
                        .build())
            .collect(Collectors.toList());

    List<BodyPart> bodyParts =
        req.getBodyPartsList()
            .stream()
            .map(
                bodypart ->
                    BodyPart.builder()
                        .bodyPartId(bodypart.getBodyPartId())
                        .userId(bodypart.getUserId())
                        .bodyPartName(bodypart.getBodyPartName())
                        .bodyPartSeq(bodypart.getBodyPartSeq())
                        .status(bodypart.getStatus())
                        .createdAt(bodypart.getCreatedAt())
                        .updatedAt(bodypart.getUpdatedAt())
                        .build())
            .collect(Collectors.toList());

    List<Image> images =
        req.getImageFiles()
            .stream()
            .map(image -> Image.builder().userId(req.getUserId()).file(image).build())
            .collect(Collectors.toList());

    PainRecord painRecord =
        PainRecord.builder()
            .painRecordId(req.getPainRecordId())
            .userId(req.getUserId())
            .painLevel(req.getPainLevel())
            .medicineList(medicines)
            .bodyPartsList(bodyParts)
            .imageList(images)
            .memo(req.getMemo())
            .updatedAt(req.getUpdatedAt())
            .createdAt(req.getCreatedAt())
            .build();

    painRecordRepository.save(painRecord);
    medicineRepository.save(painRecord);
    bodyPartRepository.save(painRecord);
    List<Image> imageListWithGCPPath =
        imageRepositoryMap.get("ImageCloudStorageRepositoryImpl").save(painRecord);
    imageRepositoryMap
        .get("ImageRepositoryImpl")
        .save(
            PainRecord.builder()
                .painRecordId(req.getPainRecordId())
                .imageList(imageListWithGCPPath)
                .build());
  }

  public List<PainRecordDto> getPainRecordList(String userId) {
    return painRecordRepository
        .findAllByUserId(userId)
        .stream()
        .map(
            v ->
                PainRecordDto.builder()
                    .painRecordId(v.getPainRecordId())
                    .userId(v.getUserId())
                    .painLevel(v.getPainLevel())
                    .memo(v.getMemo())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .collect(Collectors.toList());
  }

  public void deletePainRecord(String painRecordId) {
    painRecordRepository.delete(painRecordId);
    medicineRepository.delete(painRecordId);
    bodyPartRepository.delete(painRecordId);
  }
}
