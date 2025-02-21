package com.creative.ekart.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiResponse {
    private String message;
    private boolean status;
    private Date timestamp;
    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
        this.timestamp = new Date();
    }

}
