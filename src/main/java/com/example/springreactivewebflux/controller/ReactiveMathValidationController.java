package com.example.springreactivewebflux.controller;

import com.example.springreactivewebflux.dto.Response;
import com.example.springreactivewebflux.exception.InputValidationException;
import com.example.springreactivewebflux.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reactive/math/validation")
public class ReactiveMathValidationController {
    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("/square/{input}/mono-error")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20)
            throw new InputValidationException(input);
        return this.mathService.findSquare(input);
    }

    @GetMapping("/square/{input}/throw")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                }).cast(Integer.class)
                .flatMap(i -> this.mathService.findSquare(i));
    }
}
