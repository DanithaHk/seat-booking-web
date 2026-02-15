package com.example.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {  // ✅ Give a different class name
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();  // ✅ This is org.modelmapper.ModelMapper
    }
}
