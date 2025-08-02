package com.danzki.controller;

import com.danzki.dto.PaymentResponseDto;
import com.danzki.dto.ProductDto;
import com.danzki.dto.PaymentRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/pay")
public interface PaymentController {

    @GetMapping("/products")
    ResponseEntity<List<ProductDto>> getAllProductsByUsername(@RequestParam("username") String username);

    @GetMapping
    ResponseEntity<PaymentResponseDto> getPaymentReuslt(@RequestBody PaymentRequestDto productForPaymentDto);

}
