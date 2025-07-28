package com.danzki.dto;

import com.danzki.model.ProductType;
import lombok.Data;

@Data
public class ProductDto {
    private String accountNum;
    private Long balance;
    private String productType;
    private UserDto user;

    public ProductType getProductType() {
        try {
            return ProductType.valueOf(productType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + productType);
        }
    }
}
