package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.model.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handlerResponseStatusException(ResponseStatusException exception){

        WebResponse<String> response= WebResponse.<String>builder()
                .status(exception.getStatusCode().toString())
                .message(exception.getReason())
                .build();

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handlerDataIntegrityViolationExcpetion
            (DataIntegrityViolationException exception){

        HttpStatus httpStatus = null;
        String status = null;
        String reason = null;
        String message = exception.getMostSpecificCause().getMessage();

        if (message.contains("foreign key constraint")){
            httpStatus = HttpStatus.BAD_REQUEST;
            status = HttpStatus.BAD_REQUEST.getReasonPhrase();
            reason = "canot delete data because there is another data reference";
        }else if (message.contains("unique constraint") || message.contains("Duplicate entry")){
            httpStatus = HttpStatus.CONFLICT;
            status = HttpStatus.CONFLICT.getReasonPhrase();
            reason = "Data duplicate";
        }else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            reason = "internal server error";
        }

        WebResponse<String> response = WebResponse.<String>builder()
                .status(status)
                .message(reason)
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerConstraintViolationException(ConstraintViolationException exception){

        WebResponse<String> response= WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
