
package com.demo.tecnico.demo.error;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(
            CustomNotFoundException ex) {

        Map<String, Object> errors = new HashMap<>();

        errors.put("timestamp", LocalDateTime.now());
        errors.put("estado", HttpStatus.NOT_FOUND.value());
        errors.put("error", "No encontrado");
        errors.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestExceptions(
            CustomBadRequestException ex) {

        Map<String, Object> errors = new HashMap<>();

        errors.put("timestamp", LocalDateTime.now());
        errors.put("estado", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Error valor");
        errors.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();

        errors.put("timestamp", LocalDateTime.now());
        errors.put("estado", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Error de validación");

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        errors.put("mensaje", fieldErrors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
