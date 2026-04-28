package owl.medassist_back.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<ErrorResponse> handleBaseAppException(BaseAppException exception) {
        log.warn("Application exception: code={}, status={}, message={}", exception.getCode(), exception.getStatusCode(), exception.getMessage());
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorResponse(exception.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<String> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toReadableError)
                .toList();

        List<String> globalErrors = exception.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(this::toReadableError)
                .toList();

        List<String> errors = java.util.stream.Stream.concat(fieldErrors.stream(), globalErrors.stream()).toList();

        return validationError(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        List<String> errors = exception.getConstraintViolations()
                .stream()
                .map(v -> toReadableError(v.getPropertyPath(), v.getMessage()))
                .toList();

        return validationError(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String targetType = exception.getRequiredType() == null
                ? "unknown"
                : exception.getRequiredType().getSimpleName();
        String detail = exception.getName() + ": must be of type " + targetType;
        return validationError(List.of(detail));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ValidationErrorResponse> handleMissingParam(MissingServletRequestParameterException exception) {
        String detail = exception.getParameterName() + ": parameter is required";
        return validationError(List.of(detail));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage(HttpMessageNotReadableException exception) {
        log.debug("Request body parse error", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_REQUEST_BODY", "Malformed JSON or invalid request body."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        log.error("Unhandled exception", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Unexpected server error."));
    }

    private String toReadableError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

    private String toReadableError(ObjectError error) {
        return error.getObjectName() + ": " + error.getDefaultMessage();
    }

    private String toReadableError(Path path, String message) {
        return path + ": " + message;
    }

    private ResponseEntity<ValidationErrorResponse> validationError(List<String> errors) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponse("VALIDATION_ERROR", "Request validation failed.", errors));
    }

    public record ErrorResponse(String code, String message) {
    }

    public record ValidationErrorResponse(String code, String message, List<String> details) {
    }
}

