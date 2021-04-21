package app.itetenosuke.infrastructure.db.painrecord;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pain_records")
public class PainRecordDataModel {
  @Id
  @GeneratedValue
  private Long painrecordId;

  @Column(nullable = false)
  private Integer painLevel;

  // @Column(nullable = false)
  // private List<Medicine> medicineList;
  //
  // @Column(nullable = false)
  // private List<BodyParts> bodyPartsList;
  // private List<Image> imageList;

  @Column(nullable = false)
  private String memo;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
