package com.danzki.controller.impl;

import com.danzki.controller.PaymentController;
import com.danzki.dto.PaymentRequestDto;
import com.danzki.dto.PaymentResponseDto;
import com.danzki.dto.ProductDto;
import com.danzki.exception.ProductByUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentControllerImpl implements PaymentController {
    private final RestTemplate restTemplate;

    @Value("${services.product.host}")
    private String productHost;

    @Value("${services.product.api}")
    private String apiRoot;

    @Override
    public ResponseEntity<List<ProductDto>> getAllProductsByUsername(String username) {
        String endPointGetProductByUsername = "/user?username={username}";

        log.info("Sending request to " + productHost + endPointGetProductByUsername);
        String url = productHost + apiRoot + endPointGetProductByUsername;
        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                username
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ProductByUserException("product api response status " + response.getStatusCode().toString());
        }

        if (response.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
        }

        return ResponseEntity.ok(response.getBody());
    }

    @Override
    public ResponseEntity<PaymentResponseDto> getPaymentReuslt(PaymentRequestDto dto) {
        log.info("Start payment processing...");

        String username = dto.getUsername();
        String accountNum = dto.getAccountNum();
        String productType = dto.getProductType();
        Long payment = dto.getPayment();

        List<ProductDto> products = getAllProductsByUsername(username).getBody();

        if (products != null && products.isEmpty()) {
            throw new ProductByUserException("Products were not found for user");
        }

        boolean accountOk = products.stream()
                .anyMatch(p -> p.getAccountNum().equals(accountNum) && p.getProductType().equals(productType));
        boolean balanceOk = products.stream()
                .anyMatch(p -> p.getBalance() >= payment);

        if (!accountOk) {
            return ResponseEntity.status(404).body(PaymentResponseDto.of("Fail", "Account was not found"));
        }

        if (!balanceOk) {
            return ResponseEntity.status(404).body(PaymentResponseDto.of("Fail", "Not enough balance"));
        }

        return ResponseEntity.ok(PaymentResponseDto.of("Success", "Payment was successfully processed"));
    }
}
