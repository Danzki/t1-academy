package com.danzki.controller.impl;

import com.danzki.controller.ProductController;
import com.danzki.dto.ProductDto;
import com.danzki.mapper.ProductMapper;
import com.danzki.mapper.UserMapper;
import com.danzki.model.User;
import com.danzki.model.UserProduct;
import com.danzki.service.ProductService;
import com.danzki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final UserMapper userMapper;
    private final UserService userService;


    @Override
    public ResponseEntity<String> add(ProductDto dto) {
        UserProduct product = productService.add(dto.getAccountNum(),
                dto.getBalance(),
                dto.getProductType(),
                userMapper.toEntity(dto.getUser()));
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not created");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product ID = " + product.getId());
    }

    @Override
    public ResponseEntity<ProductDto> get(Long id) {
        UserProduct product = productService.findById(id);
        if (product == null) {
            ProductDto notFound = new ProductDto();
            notFound.setAccountNum("Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDto(product));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAll() {
        List<UserProduct> products = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDtos(products));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getByUsername(String username) {
        User user = userService.findByName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        List<UserProduct> products = productService.findAllByUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDtos(products));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        UserProduct product = productService.findById(id);
        StringBuilder sb = new StringBuilder("Product with id = ");
        sb.append(id);
        if (product == null) {
            sb.append(id).append(" not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(sb.toString());
        }
        sb.append(id).append(" deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
    }
}
