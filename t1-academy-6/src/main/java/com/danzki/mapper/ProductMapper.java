package com.danzki.mapper;

import com.danzki.dto.ProductDto;
import com.danzki.model.UserProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final UserMapper userMapper;

    public ProductDto toDto(UserProduct product) {
        ProductDto dto = new ProductDto();
        dto.setAccountNum(product.getAccountNum());
        dto.setBalance(product.getBalance());
        dto.setProductType(product.getProductType().toString());
        dto.setUser(userMapper.toDto(product.getUser()));
        return dto;
    }

    public List<ProductDto> toDtos(List<UserProduct> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }
}
