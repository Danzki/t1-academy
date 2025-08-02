package com.danzki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}