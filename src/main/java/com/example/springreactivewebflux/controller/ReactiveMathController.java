package com.example.springreactivewebflux.controller;

import com.example.springreactivewebflux.dto.MultiplyRequestDto;
import com.example.springreactivewebflux.dto.Response;
import com.example.springreactivewebflux.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/reactive/math")
public class ReactiveMathController {
    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return this.reactiveMathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return this.reactiveMathService.multiplicationTable(input);
    }

    @GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.reactiveMathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> request, @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return this.reactiveMathService.multiply(request);
    }

    @GetMapping("/search")
    public Flux<Integer> searchJob(@RequestParam("count") int count, @RequestParam("page") int page) {
        return Flux.just(count, page);
    }
}
