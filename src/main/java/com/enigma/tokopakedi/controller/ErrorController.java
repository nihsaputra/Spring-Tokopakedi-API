package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.model.WebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> handlerResponseStatusException(ResponseStatusException exception){

        WebResponse<String> response= WebResponse.<String>builder()
                .status(exception.getStatusCode().toString())
                .message(exception.getReason())
                .build();

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(response);
    }
}
