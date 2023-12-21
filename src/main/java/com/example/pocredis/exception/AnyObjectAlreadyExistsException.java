package com.example.pocredis.exception;

public class AnyObjectAlreadyExistsException extends RuntimeException {

    public AnyObjectAlreadyExistsException(Long id) {
        super(String.format("Object with id %d already exists.", id));
    }
}
