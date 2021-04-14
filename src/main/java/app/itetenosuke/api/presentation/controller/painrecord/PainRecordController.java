package app.itetenosuke.api.presentation.controller.painrecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import app.itetenosuke.api.presentation.model.PainRecordRequest;
import app.itetenosuke.domain.note.model.PainRecord;
import app.itetenosuke.domain.note.service.PainRecordService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@RestController
public class PainRecordController {
  private static final Logger logger = LoggerFactory.getLogger(PainRecordController.class);

  private final PainRecordService noteService;

  public PainRecordController(PainRecordService noteService) {
    this.noteService = noteService;
  }

  @GetMapping(path = "/v1/painrecord/{recordID}", produces = "application/json")
  public PainRecord getPainRecord(@PathVariable("recordID") Long recordID,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    PainRecord painRecord = noteService.getNote(userDetails.getUserId(), recordID);
    return painRecord;
  }

  @PostMapping(path = "/v1/painrecord/{recordID}", produces = "application/json")
  public void postPainRecord(@RequestBody PainRecordRequest painRecordRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {


  }
}
