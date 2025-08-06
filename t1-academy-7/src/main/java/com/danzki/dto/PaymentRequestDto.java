package com.danzki.dto;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private String username;
    private String accountNum;
    private String productType;
    private Long payment;
}
