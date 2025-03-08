package com.creative.ekart.payload;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class ErrorDetails {

    private final String message;
    private final HttpStatus status;
    private final String path;
    private final Date timestamp;

    public ErrorDetails(String message, String path, HttpStatus status,Date timestamp) {
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
    }
    public static ErrorDetailsBuilder builder(){
        return new ErrorDetailsBuilder();
    }
    public static class ErrorDetailsBuilder {
        private String message;
        private HttpStatus status;
        private String path;
        private Date timestamp = new Date();
        public ErrorDetails build(){
            return new ErrorDetails(message, path, status, timestamp);
        }
        public ErrorDetailsBuilder withPath(String path){
            this.path = path;
            return this;
        }
        public ErrorDetailsBuilder withStatus(HttpStatus status){
            this.status = status;
            return this;
        }
        public ErrorDetailsBuilder withMessage(String message){
            this.message = message;
            return this;
        }

    }
}
