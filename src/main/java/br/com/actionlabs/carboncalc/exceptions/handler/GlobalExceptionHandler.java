package br.com.actionlabs.carboncalc.exceptions.handler;

import br.com.actionlabs.carboncalc.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        String fieldsMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ExceptionResponse response = new ExceptionResponse(
                "Method Argument Not Valid",
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getName(),
                "Validation failed for one or more fields.",
                LocalDateTime.now(),
                fields,
                fieldsMessage
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ExceptionResponse response = new ExceptionResponse(
                "Illegal Argument Exception",
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getName(),
                ex.getMessage(),
                LocalDateTime.now(),
                "",
                ""
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(
                "Resource Not Found",
                HttpStatus.NOT_FOUND.value(),
                ex.getClass().getName(),
                ex.getMessage(),
                LocalDateTime.now(),
                "",
                ""
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
