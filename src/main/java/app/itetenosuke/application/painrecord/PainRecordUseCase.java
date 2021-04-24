package app.itetenosuke.application.painrecord;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.painrecord.IPainRecordRepository;
import app.itetenosuke.domain.painrecord.PainRecord;
import app.itetenosuke.infra.db.painrecord.PainRecordNotFoundException;
import app.itetenosuke.presentation.controller.painrecord.PainRecordReqBody;

@Service
public class PainRecordUseCase {
  private final IPainRecordRepository painRecordRepository;

  public PainRecordUseCase(IPainRecordRepository painRecordRepository) {
    this.painRecordRepository = painRecordRepository;
  }

  @Transactional(readOnly = true)
  public PainRecordDto getPainRecord(String painRecordID) {
    Optional<PainRecord> painRecord = painRecordRepository.findById(painRecordID);
    return painRecord.map(v -> new PainRecordDto(v)).orElseThrow(PainRecordNotFoundException::new);
  }

  @Transactional
  public boolean updatePainRecord(PainRecordReqBody req) {
    PainRecord painRecord =
        new PainRecord.Builder()
            .setPainRecordId(req.getPainRecordId())
            .setUserId(req.getUserId())
            .setPainLevel(req.getPainLevel())
            .setMemo(req.getMemo())
            .setUpdatedAt(req.getUpdatedAt())
            .build();
    return painRecordRepository.updatePainRecord(painRecord);
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
