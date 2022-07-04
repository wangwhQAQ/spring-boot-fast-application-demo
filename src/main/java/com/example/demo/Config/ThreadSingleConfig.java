package com.example.demo.Config;

import org.springframework.validation.annotation.Validated;

@Validated
public class ThreadSingleConfig {
    String name;
    String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
