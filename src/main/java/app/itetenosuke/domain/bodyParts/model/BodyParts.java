package app.itetenosuke.domain.bodyParts.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BodyParts {
	private Long userId;
	private Long bodyPartsId;
	
	@Min(1)
	@Max(5)
	@NotNull
	private Integer bodyPartsSeq;
	
	@Length(min = 1, max = 20)
	@NotNull
	private String bodyPartsName;
	
	@DateTimeFormat(pattern = "yyyy年M月d日H時m分")
	private LocalDateTime createdAt;
	
	@DateTimeFormat(pattern = "yyyy年M月d日H時m分")
	private LocalDateTime updatedAt;
}
