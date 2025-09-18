package com.liwanag.practice.handler;

public class ServiceInconsistencyException extends RuntimeException {
    public ServiceInconsistencyException(String message) {
        super(message);
    }
}
