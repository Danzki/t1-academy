package com.danzki.exception;

public class IncorrectOperation extends RuntimeException {
    public IncorrectOperation(String message) {
        super(message);
    }
}
