package com.danzki.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDto {
    private String status;
    private String message;

    public static PaymentResponseDto of(String status, String message) {
        return new PaymentResponseDto(status, message);
    }
}
