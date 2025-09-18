package com.liwanag.practice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, Object> missingHeader(MissingRequestHeaderException ex) {
        return Map.of("error", "Missing header", "header", ex.getHeaderName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, Object> badUUID(MethodArgumentTypeMismatchException ex) {
        return Map.of("error", "Header must be a UUID", "header", ex.getName());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, Object> notFound(NoSuchElementException ex) {
        return Map.of("error", "Not Found", "message", ex.getMessage());
    }

    @ExceptionHandler(ServiceInconsistencyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, Object> serviceInconsistency(ServiceInconsistencyException ex) {
        return Map.of("error", "Internal Server Error", "message", ex.getMessage());
    }
}
