package com.creative.ekart.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
