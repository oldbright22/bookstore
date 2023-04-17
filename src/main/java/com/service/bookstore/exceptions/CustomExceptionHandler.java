package com.service.bookstore.exceptions;

import com.service.bookstore.model.ErrorResponse;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //Get multiple errors from jakarta validations
        List<String> errors = ex.getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder().code(status.value()).timeStamp(LocalDateTime.now())
                .message(errors)
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorResponse,BAD_REQUEST);
        //return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

}
