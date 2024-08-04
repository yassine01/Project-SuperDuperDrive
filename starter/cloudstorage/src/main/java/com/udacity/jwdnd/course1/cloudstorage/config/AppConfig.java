package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    // Add a log statement or debug point here to check the values
    public void printConfig() {
        System.out.println("Max File Size: " + maxFileSize);
        System.out.println("Max Request Size: " + maxRequestSize);
    }
}
