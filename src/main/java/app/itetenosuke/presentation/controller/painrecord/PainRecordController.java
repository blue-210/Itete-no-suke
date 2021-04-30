package app.itetenosuke.presentation.controller.painrecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.itetenosuke.application.painrecord.PainRecordUseCase;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@RestController
public class PainRecordController {
  private static final Logger logger = LoggerFactory.getLogger(PainRecordController.class);

  private final PainRecordUseCase painRecordUseCase;

  public PainRecordController(PainRecordUseCase painRecordUseCase) {
    this.painRecordUseCase = painRecordUseCase;
  }

  @GetMapping(path = "/v1/painrecord/{recordID}", produces = "application/json")
  public PainRecordResBody getPainRecord(
      @PathVariable("recordID") String painRecordID,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return PainRecordResBody.of(painRecordUseCase.getPainRecord(painRecordID));
  }

  @PostMapping(path = "/v1/painrecord/{recordID}", produces = "application/json")
  public void postPainRecord(
      @RequestBody PainRecordReqBody painRecordRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    painRecordUseCase.createPainRecord(painRecordRequest);
  }
}
