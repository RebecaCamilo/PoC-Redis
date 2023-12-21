package com.example.pocredis.exception;

public class AnyObjectNotFoundException extends RuntimeException {

    public AnyObjectNotFoundException(Long id) {
        super(String.format("Object with id %d not found.", id));
    }
}
