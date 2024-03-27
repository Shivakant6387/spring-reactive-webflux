package com.example.springreactivewebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8866")
                .defaultHeaders(headers -> headers.setBasicAuth("Shivakant Singh", "Do not disturb"))
                .build();
    }

    @Bean
    public WebClient webClients() {
        return WebClient.builder()
                .baseUrl("http://localhost:8866")
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchangeFunction) {
        System.out.println("generating session token");
        ClientRequest build = ClientRequest.from(request)
                .headers(headers -> headers.setBasicAuth("some-lengthy-jwt"))
                .build();
        return exchangeFunction.exchange(build);
    }
}
