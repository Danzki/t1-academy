package com.danzki.exception;

public class TooManyStaticMethods extends RuntimeException {
    public TooManyStaticMethods(String message) {
        super(message);
    }
}
