package upp.la.util;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import upp.la.error.ApiError;
import upp.la.model.exceptions.*;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = "Malformed JSON request";
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
  }

  @ExceptionHandler(ConversionFailedError.class)
  protected ResponseEntity<Object> handleConversionFailedError(ConversionFailedError ex) {
    ApiError apiError = new ApiError(UNPROCESSABLE_ENTITY);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(DuplicateEntity.class)
  protected ResponseEntity<Object> handleDuplicateEntity(DuplicateEntity ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(EntityNotFound.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFound ex) {
    ApiError apiError = new ApiError(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(UnexpectedError.class)
  protected ResponseEntity<Object> handleUnexpectedError(UnexpectedError ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(AuthorizationError.class)
  protected ResponseEntity<Object> handleInvalidEmailOrPasswordError(AuthorizationError ex) {
    ApiError apiError = new ApiError(UNAUTHORIZED);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ValidationError.class)
  protected ResponseEntity<Object> handleValidationError(AuthorizationError ex) {
    ApiError apiError = new ApiError(UNPROCESSABLE_ENTITY);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(FileError.class)
  protected ResponseEntity<Object> handleFileError(AuthorizationError ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(BpmnError.class)
  protected ResponseEntity<Object> handleBpmnError(AuthorizationError ex) {
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
    apiError.setMessage(ex.getMessage());
    apiError.setEx(ex);
    return buildResponseEntity(apiError);
  }
}
