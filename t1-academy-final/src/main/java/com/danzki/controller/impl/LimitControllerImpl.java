package com.danzki.controller.impl;

import com.danzki.controller.LimitController;
import com.danzki.dto.request.PaymentRequestDto;
import com.danzki.dto.response.InfoResponseDto;
import com.danzki.dto.response.LimitResponseDto;
import com.danzki.dto.response.PaymentResponseDto;
import com.danzki.process.LimitProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitControllerImpl implements LimitController {
    private final LimitProcessor processor;

    @Override
    public ResponseEntity<LimitResponseDto> getById(Long id) {
        LimitResponseDto dto = processor.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<LimitResponseDto> getByClientId(Long id) {
        LimitResponseDto dto = processor.findByClientId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<InfoResponseDto> deleteByClientId(Long id) {
        InfoResponseDto dto = processor.deleteByClientId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<PaymentResponseDto> paymentApply(@RequestBody PaymentRequestDto dto) {
        PaymentResponseDto response = processor.apply(dto);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        if (response.getError() != null) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaymentResponseDto> paymentRevert(@RequestBody PaymentRequestDto dto) {
        PaymentResponseDto response = processor.revert(dto);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InfoResponseDto> restoreAllLimits() {
        InfoResponseDto dto = processor.restoreAllLimits();
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<InfoResponseDto> test() {
        return ResponseEntity.ok(processor.test());
    }
}
