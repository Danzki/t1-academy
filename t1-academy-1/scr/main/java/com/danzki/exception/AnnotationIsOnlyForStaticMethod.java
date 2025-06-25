package com.danzki.exception;

public class AnnotationIsOnlyForStaticMethod extends RuntimeException {
    public AnnotationIsOnlyForStaticMethod(String message) {
        super(message);
    }
}
