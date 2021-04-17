package app.itetenosuke.api.application.painrecord;

import java.util.Optional;
import app.itetenosuke.api.domain.repository.painrecord.PainRecordRepository;
import app.itetenosuke.api.infrastructure.db.painrecord.PainRecordDataModel;
import app.itetenosuke.api.presentation.model.PainRecordRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PainRecordUseCase {
  private final PainRecordRepository painRecordRepository;

  public PainRecordUseCase(PainRecordRepository painRecordRepository) {
    this.painRecordRepository = painRecordRepository;
  }

  @Transactional(readOnly = true)
  public PainRecordDataModel getPainRecord(Long painRecordID) {
    Optional<PainRecordDataModel> painRecordDataModel = painRecordRepository.findById(painRecordID);
    return painRecordDataModel.orElse(null);
  }

  @Transactional
  public PainRecordDataModel updatePainRecord(PainRecordRequest painRecordReq) {
    PainRecordDataModel painRecord = getPainRecord(painRecordReq.getPainRecordID());
    painRecord.setPainLevel(painRecordReq.getPainLevel());
    painRecord.setMemo(painRecordReq.getMemo());
    painRecord.setUpdatedAt(painRecordReq.getUpdatedAt());

    return painRecord;
  }
}
