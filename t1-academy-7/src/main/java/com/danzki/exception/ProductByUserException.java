package com.danzki.exception;

public class ProductByUserException extends RuntimeException {
    public ProductByUserException(String message) {
      super(message);
    }
}
