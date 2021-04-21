package app.itetenosuke.presentation.controller.painrecord;

import app.itetenosuke.application.painrecord.PainRecordUseCase;
import app.itetenosuke.domain.user.model.UserDetailsImpl;
import app.itetenosuke.infrastructure.db.painrecord.PainRecordDataModel;
import app.itetenosuke.presentation.model.PainRecordRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PainRecordController {
  private static final Logger logger = LoggerFactory.getLogger(PainRecordController.class);

  private final PainRecordUseCase painRecordUseCase;

  public PainRecordController(PainRecordUseCase painRecordUseCase) {
    this.painRecordUseCase = painRecordUseCase;
  }

  @GetMapping(path = "/v1/painrecord/{recordID}", produces = "application/json")
  public PainRecordDataModel getPainRecord(@PathVariable("recordID") Long painRecordID,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return painRecordUseCase.getPainRecord(painRecordID);
  }

  @PostMapping(path = "/v1/painrecord/{recordID}", produces = "application/json")
  public void postPainRecord(@RequestBody PainRecordRequest painRecordRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    painRecordUseCase.updatePainRecord(painRecordRequest);
  }
}
