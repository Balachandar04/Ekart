package com.creative.ekart;

import org.hibernate.annotations.processing.Exclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication

public class EkartApplication {

    public static void main(String[] args) {
        SpringApplication.run(EkartApplication.class, args);
    }

}
