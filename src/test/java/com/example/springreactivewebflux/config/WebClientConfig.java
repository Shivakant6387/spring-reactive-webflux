package com.example.springreactivewebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8866")
                .defaultHeaders(headers -> headers.setBasicAuth("Shivakant Singh","Do not disturb"))
                .build();
    }
}
