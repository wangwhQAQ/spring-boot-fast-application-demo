package com.example.demo.Model;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class valueTest {
    @Value("${value}")
    public String value;
}
