package com.stemm.pubsub.service.auth;

public class DuplicateValueException extends RuntimeException {
    public DuplicateValueException(String message) {
        super(message);
    }
}
