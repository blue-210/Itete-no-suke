package app.itetenosuke.api.presentation.controller.shared;

import lombok.Value;

@Value
public class ErrorResBody {
  private final String message;
  private final Integer code;
}
