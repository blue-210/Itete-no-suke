package app.itetenosuke.api.presentation.controller.medicine;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.itetenosuke.api.application.medicine.MedicineUseCase;
import app.itetenosuke.api.presentation.controller.shared.MedicineReqBody;
import app.itetenosuke.api.presentation.controller.shared.MedicineResBody;
import app.itetenosuke.domain.user.model.UserDetailsImpl;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MedicineController {
  private final MedicineUseCase medicineUseCase;

  @GetMapping(path = "/v1/medicines", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<MedicineResBody> getMedicineList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return medicineUseCase
        .getMedicineList(userDetails.getUserId())
        .stream()
        .map(
            v ->
                MedicineResBody.builder()
                    .medicineId(v.getMedicineId())
                    .medicineName(v.getMedicineName())
                    .status(v.getStatus())
                    .createdAt(v.getCreatedAt())
                    .updatedAt(v.getUpdatedAt())
                    .build())
        .collect(Collectors.toList());
  }

  @PostMapping(path = "/v1/medicines", produces = MediaType.APPLICATION_JSON_VALUE)
  public MedicineResBody createMedicine(
      @RequestBody MedicineReqBody medicineReqBody,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    String medicineId = medicineUseCase.createMedicine(medicineReqBody);
    return MedicineResBody.of(medicineUseCase.getMedicine(medicineId));
  }

  @PutMapping(path = "/v1/medicines/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public MedicineResBody updateMedicine(
      @RequestBody MedicineReqBody medicineReqBody,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    String medicineId = medicineUseCase.updateMedicine(medicineReqBody);
    return MedicineResBody.of(medicineUseCase.getMedicine(medicineId));
  }
}