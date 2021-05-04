package app.itetenosuke.api.presentation.controller.bodypart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import app.itetenosuke.api.application.bodypart.BodyPartUseCase;
import app.itetenosuke.api.presentation.controller.shared.BodyPartResBody;
import app.itetenosuke.domain.user.model.UserDetailsImpl;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BodyPartController {
  private final BodyPartUseCase bodyPartUseCase;

  @GetMapping(path = "/v1/bodyparts", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<BodyPartResBody> getBodyPartList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return bodyPartUseCase
        .getBodyPartList(userDetails.getUserId())
        .stream()
        .map(v -> BodyPartResBody.of(v))
        .collect(Collectors.toList());
  }

  @GetMapping(path = "/v1/bodyparts/{bodyPartId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BodyPartResBody getBodyPart(
      @PathVariable String bodyPartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return BodyPartResBody.of(bodyPartUseCase.getBodyPart(bodyPartId));
  }
}
