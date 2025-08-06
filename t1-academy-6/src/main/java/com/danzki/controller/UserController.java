package com.danzki.controller;

import com.danzki.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {
    @PostMapping("/add")
    ResponseEntity<UserDto> add(@RequestParam String userName);

    @GetMapping("/get/{id}")
    ResponseEntity<UserDto> get(@PathVariable Long id);

    @GetMapping("/getall")
    ResponseEntity<List<UserDto>> getAll();

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable Long id);
}
