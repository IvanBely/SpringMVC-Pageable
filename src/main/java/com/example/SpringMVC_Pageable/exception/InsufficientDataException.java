package com.example.SpringMVC_Pageable.exception;

public class InsufficientDataException extends RuntimeException {
    public InsufficientDataException(String message) {
        super(message);
    }
}
