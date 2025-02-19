package com.creative.ekart.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private String message;
    private HttpStatus statusCode;

    public ResourceNotFoundException(String message) {
        this.message = message;
        this.statusCode = HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
