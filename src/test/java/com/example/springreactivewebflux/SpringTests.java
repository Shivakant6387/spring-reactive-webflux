package com.example.springreactivewebflux;

import com.example.springreactivewebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class SpringTests extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        Response response = this.webClient
                .get()
                .uri("/api/reactive/math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();
        System.out.println(response);
    }

    @Test
    public void stepVerifierTest() {
        Mono<Response> response = this.webClient
                .get()
                .uri("/api/reactive/math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class);
        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();
    }

    @Test
    public void fluxTest() {
        Flux<Response> response = this.webClient
                .get()
                .uri("/api/reactive/math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxStreamTest() {
        Flux<Response> response = this.webClient
                .get()
                .uri("/api/reactive/math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }
}
