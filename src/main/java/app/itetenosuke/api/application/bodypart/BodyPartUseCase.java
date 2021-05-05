package app.itetenosuke.api.application.bodypart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.api.domain.bodypart.BodyPart;
import app.itetenosuke.api.domain.bodypart.IBodyPartRepository;
import app.itetenosuke.api.domain.shared.Status;
import app.itetenosuke.api.infra.db.bodypart.BodyPartNotFouncException;
import app.itetenosuke.api.presentation.controller.shared.BodyPartReqBody;
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

  @Transactional(readOnly = true)
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

  public String createBodyPart(BodyPartReqBody req) {
    BodyPart bodyPart =
        BodyPart.builder()
            .bodyPartId(req.getBodyPartId())
            .userId(req.getUserId())
            .bodyPartName(req.getBodyPartName())
            .status(Status.ALIVE.toString())
            .createdAt(req.getCreatedAt())
            .updatedAt(req.getUpdatedAt())
            .build();
    bodyPartRepository.save(bodyPart);
    return bodyPart.getBodyPartId();
  }

  public String updateBodyPart(BodyPartReqBody req) {
    BodyPart bodyPart =
        BodyPart.builder()
            .bodyPartId(req.getBodyPartId())
            .userId(req.getUserId())
            .bodyPartName(req.getBodyPartName())
            .status(Status.ALIVE.toString())
            .createdAt(req.getCreatedAt())
            .updatedAt(req.getUpdatedAt())
            .build();
    bodyPartRepository.save(bodyPart);
    return bodyPart.getBodyPartId();
  }
}
