package com.danzki.controller.impl;

import com.danzki.controller.UserController;
import com.danzki.dto.UserDto;
import com.danzki.mapper.UserMapper;
import com.danzki.model.User;
import com.danzki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserDto> add(String userName) {
        User user = userService.add(userName);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(user));
    }

    @Override
    public ResponseEntity<UserDto> get(Long id) {
        User user = userService.find(id);
        if (user == null) {
            UserDto notFound = new UserDto();
            notFound.setUsername("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(user));
    }

    @Override
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDtos(users));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        User user = userService.find(id);
        StringBuilder sb = new StringBuilder("User with id = ");
        sb.append(id);
        if (user == null) {
            sb.append(id).append(" not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(sb.toString());
        }
        sb.append(id).append(" deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
    }
}
