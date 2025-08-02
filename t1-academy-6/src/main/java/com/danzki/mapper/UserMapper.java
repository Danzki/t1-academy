package com.danzki.mapper;

import com.danzki.dto.UserDto;
import com.danzki.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "products", ignore = true)
    User toEntity(UserDto dto);

    UserDto toDto(User user);

    @Mapping(target = "products", ignore = true)
    List<UserDto> toDtos(List<User> users);
}
