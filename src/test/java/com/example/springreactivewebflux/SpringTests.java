package com.example.springreactivewebflux;

import com.example.springreactivewebflux.dto.MultiplyRequestDto;
import com.example.springreactivewebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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

    @Test
    public void postTest() {
        Mono<Response> response = this.webClient
                .post()
                .uri("/api/reactive/math/multiply")
                .bodyValue(multiplyRequestDto(5, 5))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void postHeaderTest() {
        Mono<Response> response = this.webClient
                .post()
                .uri("/api/reactive/math/multiply")
                .bodyValue(multiplyRequestDto(5, 5))
                .headers(h -> h.set("someKey", "someValue"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDto multiplyRequestDto(int num1, int num2) {
        MultiplyRequestDto multiplyRequestDto = new MultiplyRequestDto();
        multiplyRequestDto.setFirst(num1);
        multiplyRequestDto.setSecond(num2);
        return multiplyRequestDto;
    }

    @Test
    public void badRequestTest() {
        Mono<Response> response = this.webClient
                .get()
                .uri("/api/reactive/math/validation/square/{input}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);
        StepVerifier.create(response)
                .verifyError(WebClientResponseException.class);
    }

}
