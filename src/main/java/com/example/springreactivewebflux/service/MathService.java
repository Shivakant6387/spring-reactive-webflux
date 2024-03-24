package com.example.springreactivewebflux.service;

import com.example.springreactivewebflux.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {
    public Response findSquare(int input) {
        return new Response(input * input);
    }

    public List<Response> multiplicationTable(int input) {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSecond(1))
                .peek(i -> System.out.println("math-service processing :" + i))
                .mapToObj(i -> new Response(i * input))
                .collect(Collectors.toList());
    }

}
