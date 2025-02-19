package com.creative.ekart.exception;

import java.util.Date;

public class ErrorDetails {
    private final String message;
    private final Date timestamp;


    public ErrorDetails(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;

    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
