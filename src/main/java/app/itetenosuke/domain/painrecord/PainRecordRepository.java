package app.itetenosuke.api.domain.repository.painrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import app.itetenosuke.api.infrastructure.db.painrecord.PainRecordDataModel;

public interface PainRecordRepository extends JpaRepository<PainRecordDataModel, Long> {
}
