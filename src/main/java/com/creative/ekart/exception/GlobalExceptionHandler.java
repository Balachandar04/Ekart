package com.creative.ekart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),new Date());
        return new ResponseEntity<>(errorDetails, e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
                );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(ResourceNotFoundException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),new Date());
        return new ResponseEntity<>(errorDetails, e.getStatusCode());
    }
}
