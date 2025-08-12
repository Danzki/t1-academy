package com.danzki.exception.handler;

import com.danzki.dto.response.ErrorHandlerDto;
import com.danzki.exception.IncorrectOperation;
import com.danzki.exception.RestoreAllLimitsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IncorrectOperation.class)
    public ResponseEntity<ErrorHandlerDto> handleHttpClientError(IncorrectOperation ex, WebRequest request) {
        log.info("Error! " + ex.getMessage());
        ErrorHandlerDto error = new ErrorHandlerDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestoreAllLimitsException.class)
    public ResponseEntity<ErrorHandlerDto> handleHttpClientError(RestoreAllLimitsException ex, WebRequest request) {
        log.info("Error! " + ex.getMessage());
        ErrorHandlerDto error = new ErrorHandlerDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorHandlerDto> handleHttpClientError(RuntimeException ex, WebRequest request) {
        log.info("Error! " + ex.getMessage());
        ErrorHandlerDto error = new ErrorHandlerDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
