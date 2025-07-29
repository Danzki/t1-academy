package com.danzki.mapper;

import com.danzki.dto.ProductDto;
import com.danzki.model.UserProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
//    @Mapping(target = "id", ignore = true)
    ProductDto toDto(UserProduct product);

//    @Mapping(target = "id", ignore = true)
    List<ProductDto> toDtos(List<UserProduct> products);
}
