package com.example.springreactivewebflux.exceptionHandler;

import com.example.springreactivewebflux.dto.InputFailedValidationResponse;
import com.example.springreactivewebflux.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException e) {
        InputFailedValidationResponse response = new InputFailedValidationResponse();
        response.setErrorCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        response.setInput(e.getInput());
        return ResponseEntity.badRequest().body(response);
    }
}
