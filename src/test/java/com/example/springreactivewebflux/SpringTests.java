package com.example.springreactivewebflux;

import com.example.springreactivewebflux.dto.InputFailedValidationResponse;
import com.example.springreactivewebflux.dto.MultiplyRequestDto;
import com.example.springreactivewebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

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
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void badRequestExchangeTest() {
        Mono<Object> response = this.webClient
                .get()
                .uri("/api/reactive/math/validation/square/{input}/throw", 5)
                .exchangeToMono(ClientResponse::createError)
                .doOnNext(System.out::println)
                .doOnError(System.out::println);
        StepVerifier.create(response)
                .verifyError(WebClientResponseException.class);
    }

    String queryParams = "http://localhost:8866/api/reactive/math/search?count={count}&page={page}";

    @Test
    public void queryParamses() {
        URI uri = UriComponentsBuilder.fromHttpUrl(queryParams)
                .build(10, 20);
        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);
        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void queryParams() {
        Map<String, Integer> map = Map.of("count", 20, "page", 30);
        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(b -> b.path("/api/reactive/math/search").query("count={count}&page={page}").build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);
        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
