package app.itetenosuke.api.presentation.controller.painrecord;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.itetenosuke.api.application.painrecord.PainRecordUseCase;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@RestController
public class PainRecordController {

  private final PainRecordUseCase painRecordUseCase;

  public PainRecordController(PainRecordUseCase painRecordUseCase) {
    this.painRecordUseCase = painRecordUseCase;
  }

  @GetMapping(path = "/v1/painrecords/{recordID}", produces = "application/json")
  public PainRecordResBody getPainRecord(
      @PathVariable("recordID") String painRecordID,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return PainRecordResBody.of(painRecordUseCase.getPainRecord(painRecordID));
  }

  @GetMapping(path = "/v1/painrecords", produces = "application/json")
  public List<PainRecordResBody> getPainRecordList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return painRecordUseCase
        .getPainRecordList(userDetails.getUserId())
        .stream()
        .map(painRecord -> PainRecordResBody.of(painRecord))
        .collect(Collectors.toList());
  }

  @PostMapping(path = "/v1/painrecords/{recordID}", produces = "application/json")
  public void postPainRecord(
      @RequestBody PainRecordReqBody painRecordRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    painRecordUseCase.createPainRecord(painRecordRequest);
  }
}
