package com.example.springreactivewebflux.config;

import com.example.springreactivewebflux.dto.InputFailedValidationResponse;
import com.example.springreactivewebflux.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {
    @Autowired
    private RequestHeader requestHeader;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHeader::squareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
                .GET("table/{input}", requestHeader::tableHandler)
                .GET("table/{input}/stream", requestHeader::tableStreamHandler)
                .POST("multiply", requestHeader::multiplyHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .GET("square/{input}/validation", requestHeader::squareHandlerWithValidation)
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (error, request) -> {
            InputValidationException exception = (InputValidationException) error;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(exception.getInput());
            response.setMessage(exception.getMessage());
            response.setErrorCode(exception.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
