package app.itetenosuke.api.presentation.controller.shared;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {

      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
    }
    Object obj = body == null ? new ErrorResBody(ex.getMessage(), status.value()) : body;
    return new ResponseEntity<>(obj, headers, status);
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest req) {
    HttpHeaders headers = new HttpHeaders();
    ErrorResBody body = new ErrorResBody(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    HttpStatus status = HttpStatus.NOT_FOUND;
    return this.handleExceptionInternal(ex, body, headers, status, req);
  }
}
