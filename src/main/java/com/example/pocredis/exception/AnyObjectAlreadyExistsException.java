package com.example.pocredis.exception;

public class AnyObjectAlreadyExistsException extends RuntimeException {

    public AnyObjectAlreadyExistsException(String description) {
        super(String.format("Object with description %s already exists.", description));
    }
}
