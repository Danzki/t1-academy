package com.danzki.mapper;

import com.danzki.dto.response.LimitResponseDto;
import com.danzki.model.Limit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LimitResponseMapper {
    @Mapping(target = "id", ignore = true)
    Limit toEntity(LimitResponseDto dto);

    LimitResponseDto toDto(Limit limit);
}
