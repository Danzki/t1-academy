package com.danzki.controller;

import com.danzki.controller.impl.LimitControllerImpl;
import com.danzki.dto.request.PaymentRequestDto;
import com.danzki.dto.response.InfoResponseDto;
import com.danzki.dto.response.LimitResponseDto;
import com.danzki.dto.response.PaymentResponseDto;
import com.danzki.mapper.LimitResponseMapper;
import com.danzki.model.Limit;
import com.danzki.model.ResponseStatus;
import com.danzki.process.LimitProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LimitControllerImplTest {

    @Mock
    private LimitProcessor processor;

    @Mock
    private LimitResponseMapper mapper;

    @InjectMocks
    private LimitControllerImpl limitController;

    private Limit limit;
    private LimitResponseDto limitDto;

    @BeforeEach
    void setUp() {
        limit = new Limit(1L, 1L, 10000.00);
        limitDto = new LimitResponseDto();
        limitDto.setClientId(1L);
        limitDto.setCurrentValue(10000.00);
        lenient().when(mapper.toDto(limit)).thenReturn(limitDto);
    }

    @Test
    @DisplayName("getById - success")
    void getById_shouldReturnLimit() {
        when(processor.findById(anyLong()))
                .thenReturn(limitDto);

        ResponseEntity<LimitResponseDto> response = limitController.getById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(limitDto, response.getBody());
        verify(processor, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getById - should return 500 when error occurs")
    void getById_shouldHandleException() {
        when(processor.findById(anyLong())).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> limitController.getById(1L));
    }

    @Test
    @DisplayName("getById - not found 404")
    void getById_shouldReturn404NotFound() {
        when(processor.findById(anyLong()))
                .thenReturn(null);

        ResponseEntity<LimitResponseDto> response = limitController.getById(2L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(processor, times(1)).findById(2L);
    }

    @Test
    @DisplayName("getByClientId - success")
    void getByClientId_shouldReturnLimit() {
        when(processor.findByClientId(anyLong()))
                .thenReturn(limitDto);

        ResponseEntity<LimitResponseDto> response = limitController.getByClientId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(limitDto, response.getBody());
        verify(processor, times(1)).findByClientId(1L);

    }

    @Test
    @DisplayName("getByClientId - 404 not found")
    void getByClientId_shouldReturn404NotFound() {
        when(processor.findByClientId(anyLong()))
                .thenReturn(null);

        ResponseEntity<LimitResponseDto> response = limitController.getByClientId(2L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(processor, times(1)).findByClientId(2L);

    }

    @Test
    @DisplayName("deleteByClientId - return 1")
    void deleteByClientId_shouldReturn1() {
        Long testId = 1L;
        InfoResponseDto infoDeleteDto = InfoResponseDto.builder()
                .message("1 limit was deleted.")
                .build();

        when(processor.deleteByClientId(testId))
                .thenReturn(infoDeleteDto);

        ResponseEntity<InfoResponseDto> response = limitController.deleteByClientId(testId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(infoDeleteDto, response.getBody());
        verify(processor, times(1)).deleteByClientId(testId);

    }

    @Test
    @DisplayName("applyPayment - SUCCESS. Return DTO")
    void applyPayment_shouldReturnPaymentDto() {
        Long testId = 1L;
        Double paymentAmount = 500.00;

        PaymentRequestDto dtoReq = new PaymentRequestDto();
        dtoReq.setClientId(testId);
        dtoReq.setPayment(paymentAmount);

        Double newLimit = limit.getCurrentValue() - paymentAmount;

        PaymentResponseDto dtoResp = PaymentResponseDto.builder()
                .status(ResponseStatus.SUCCESS.name())
                .message("Лимит изменен. Новый лимит = " + newLimit)
                .build();

        when(processor.apply(dtoReq))
                .thenReturn(dtoResp);

        ResponseEntity<PaymentResponseDto> response = limitController.paymentApply(dtoReq);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(dtoResp, response.getBody());
        verify(processor, times(1)).apply(dtoReq);

    }

    @Test
    @DisplayName("applyPayment - overlimit. Return 400")
    void applyPayment_shouldReturnPaymentFailed() {
        Long testId = 1L;
        Double paymentAmount = 15000.00;

        PaymentRequestDto dtoReq = new PaymentRequestDto();
        dtoReq.setClientId(testId);
        dtoReq.setPayment(paymentAmount);

        PaymentResponseDto dtoResp = PaymentResponseDto.builder()
                .status(ResponseStatus.ERROR.name())
                .error("Превышен дневной лимит переводов")
                .build();

        when(processor.apply(dtoReq))
                .thenReturn(dtoResp);

        ResponseEntity<PaymentResponseDto> response = limitController.paymentApply(dtoReq);

        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(dtoResp, response.getBody());
        verify(processor, times(1)).apply(dtoReq);

    }

    @Test
    @DisplayName("revertPayment - SUCCESS. return DTO")
    void revertPayment_shouldReturnPaymentDto() {
        Long testId = 1L;
        Double paymentAmount = 500.00;

        PaymentRequestDto dtoReq = new PaymentRequestDto();
        dtoReq.setClientId(testId);
        dtoReq.setPayment(paymentAmount);

        PaymentResponseDto dtoResp = PaymentResponseDto.builder()
                .status(ResponseStatus.SUCCESS.name())
                .message("Лимит восстановлен. Новый лимит = " + limit.getCurrentValue())
                .build();

        when(processor.revert(dtoReq))
                .thenReturn(dtoResp);

        ResponseEntity<PaymentResponseDto> response = limitController.paymentRevert(dtoReq);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(dtoResp, response.getBody());
        verify(processor, times(1)).revert(dtoReq);

    }
}
