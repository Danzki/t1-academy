package com.danzki.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String accountNum;
    private Long balance;
    private String productType;
    private UserDto user;

}
