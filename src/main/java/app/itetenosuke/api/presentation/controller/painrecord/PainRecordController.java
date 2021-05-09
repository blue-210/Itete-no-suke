package app.itetenosuke.api.presentation.controller.painrecord;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.itetenosuke.api.application.painrecord.PainRecordUseCase;
import app.itetenosuke.api.domain.user.UserDetailsImpl;
import app.itetenosuke.api.presentation.controller.shared.PainRecordReqBody;
import app.itetenosuke.api.presentation.controller.shared.PainRecordResBody;

// TODO /v1部分を共通パスとしてマッピングする
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

  @PostMapping(path = "/v1/painrecords", produces = MediaType.APPLICATION_JSON_VALUE)
  public PainRecordResBody postPainRecord(
      @RequestBody PainRecordReqBody painRecordRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    painRecordUseCase.createPainRecord(painRecordRequest);
    return PainRecordResBody.of(
        painRecordUseCase.getPainRecord(painRecordRequest.getPainRecordId()));
  }

  @PutMapping(path = "/v1/painrecords/{recordID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PainRecordResBody putPainRecord(
      @PathVariable("recordID") String painRecordID,
      @RequestBody PainRecordReqBody painRecordRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    painRecordUseCase.updatePainRecord(painRecordRequest);
    return PainRecordResBody.of(painRecordUseCase.getPainRecord(painRecordID));
  }

  @DeleteMapping(path = "/v1/painrecords/{recordID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deletePainRecord(
      @PathVariable("recordID") String painRecordID,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    painRecordUseCase.deletePainRecord(painRecordID);
    return ResponseEntity.ok(Collections.EMPTY_MAP);
  }
}
