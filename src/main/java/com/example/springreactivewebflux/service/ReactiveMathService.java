package com.example.springreactivewebflux.service;

import com.example.springreactivewebflux.dto.MultiplyRequestDto;
import com.example.springreactivewebflux.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {
    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
//        List<Response> list = IntStream.rangeClosed(1, 10)
//                .peek(i -> SleepUtil.sleepSecond(1))
//                .peek(i -> System.out.println("math-service processing :" + i))
//                .mapToObj(i -> new Response(i * input))
//                .collect(Collectors.toList());
//        return Flux.fromIterable(list);
        return Flux.range(1, 10)
//                .doOnNext(i -> SleepUtil.sleepSecond(1))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("reactive-math-service processing :" + i))
                .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> request) {
        return request.map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
}
