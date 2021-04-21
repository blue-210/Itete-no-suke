package app.itetenosuke.domain.painrecord;

import org.springframework.data.jpa.repository.JpaRepository;

import app.itetenosuke.infrastructure.db.painrecord.PainRecordDataModel;

public interface PainRecordRepository extends JpaRepository<PainRecordDataModel, Long> {
}
