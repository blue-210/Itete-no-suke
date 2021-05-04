package app.itetenosuke.api.application.bodypart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.bodypart.IBodyPartRepository;
import app.itetenosuke.api.infra.db.bodypart.BodyPartNotFouncException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BodyPartUseCase {
  IBodyPartRepository bodyPartRepository;

  @Transactional(readOnly = true)
  public List<BodyPartDto> getBodyPartList(String userId) {
    return bodyPartRepository
        .findAllByUserId(userId)
        .stream()
        .map(
            v ->
                BodyPartDto.builder()
                    .bodyPartId(v.getBodyPartId())
                    .userId(v.getUserId())
                    .bodyPartName(v.getBodyPartName())
                    .status(v.getStatus())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .collect(Collectors.toList());
  }

  public BodyPartDto getBodyPart(String bodyPartId) {
    return bodyPartRepository
        .findByBodyPartId(bodyPartId)
        .map(
            v ->
                BodyPartDto.builder()
                    .bodyPartId(v.getBodyPartId())
                    .userId(v.getUserId())
                    .bodyPartName(v.getBodyPartName())
                    .status(v.getStatus())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .orElseThrow(BodyPartNotFouncException::new);
  }
}
