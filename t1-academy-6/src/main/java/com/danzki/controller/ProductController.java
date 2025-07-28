package com.danzki.controller;

import com.danzki.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
public interface ProductController {

    @PostMapping("/add")
    ResponseEntity<String> add(@RequestBody ProductDto dto);

    @GetMapping("/get/{id}")
    ResponseEntity<ProductDto> get(@PathVariable Long id);

    @GetMapping("/getall")
    ResponseEntity<List<ProductDto>> getAll();

    @GetMapping("/get_by_user/{username}")
    ResponseEntity<List<ProductDto>> getByUsername(@PathVariable String username);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable Long id);
}
