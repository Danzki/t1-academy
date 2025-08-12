package com.danzki.process;

import com.danzki.config.AppConfig;
import com.danzki.dto.request.PaymentRequestDto;
import com.danzki.dto.response.InfoResponseDto;
import com.danzki.dto.response.LimitResponseDto;
import com.danzki.dto.response.PaymentResponseDto;
import com.danzki.mapper.LimitResponseMapper;
import com.danzki.model.Limit;
import com.danzki.model.ResponseStatus;
import com.danzki.service.LimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LimitProcessor {
    private final LimitService limitService;
    private final LimitResponseMapper limitResponseMapper;
    private final AppConfig config;

    public PaymentResponseDto add(Long userId) {
        Double limitAmount = config.getDefaultLimitValue();
        Limit limit = limitService.add(userId, limitAmount);
        if (limit == null) {
            log.info("Limit not found for user with id = " + userId);
            return getErrorDto("Limit not found for user with id = " + userId);
        }
        log.info("Limit was added for user with id = " + userId);
        return getSuccessDto("Limit was added for user with id = " + userId);
    }

    public LimitResponseDto findById(Long id) {
        Limit limit = limitService.findById(id);
        if (limit == null) {
            log.info("limit not found ");
            return null;
        }
        log.info("limit found ");
        return limitResponseMapper.toDto(limit);
    }

    public LimitResponseDto findByClientId(Long clientId) {
        Limit limit = limitService.findByClientId(clientId);
        if (limit == null) {
            log.info("limit not found ");
            return null;
        }
        log.info("limit found ");
        return limitResponseMapper.toDto(limit);
    }

    public InfoResponseDto deleteByClientId(Long clientId) {
        Integer count = limitService.deleteByClientId(clientId);
        return InfoResponseDto.builder()
                .message(count + " limit was deleted.")
                .build();
    }

    private PaymentResponseDto getErrorDto(String error) {
        log.debug("error: " + error);
        return PaymentResponseDto.builder()
                .status(ResponseStatus.ERROR.name())
                .error(error)
                .build();
    }

    private PaymentResponseDto getSuccessDto(String message) {
        log.debug("success: " + message);
        return PaymentResponseDto.builder()
                .status(ResponseStatus.SUCCESS.name())
                .message(message)
                .build();
    }

    private Limit getOrAddLimit(Long clientId) {
        Limit limit = limitService.findByClientId(clientId);
        if (limit == null) {
            limit = limitService.add(clientId, config.getDefaultLimitValue());
        }
        return limit;
    }

    public PaymentResponseDto apply(PaymentRequestDto dto) {
        Long clientId = dto.getClientId();
        Double amount = dto.getPayment();
        log.info(".apply: обработка операции для userid - " + clientId);
        log.info(String.format("clientId=%s, amount=%s", clientId, amount));
        Limit limit = getOrAddLimit(clientId);
        Double currentValue = limit.getCurrentValue();
        currentValue -= amount;

        if (currentValue < 0) {
            return getErrorDto("Превышен дневной лимит переводов.");
        }

        limit = limitService.update(clientId, currentValue);
        return getSuccessDto("Лимит изменен. Новый лимит = " + limit.getCurrentValue());
    }

    public PaymentResponseDto revert(PaymentRequestDto dto) {
        Long clientId = dto.getClientId();
        Double amount = dto.getPayment();

        log.info(".revert: обработка операции для userid - " + clientId);
        log.info(String.format("userId=%s, amount=%s", clientId, amount));
        Limit limit = getOrAddLimit(clientId);
        Double currentValue = limit.getCurrentValue();
        currentValue += amount;

        limit = limitService.update(clientId, currentValue);
        return getSuccessDto("Лимит восстановлен. Новый лимит = " + limit.getCurrentValue());
    }

    public InfoResponseDto restoreAllLimits() {
        Double amount = config.getDefaultLimitValue();
        Integer count = limitService.updateAllLimits(amount);
        return InfoResponseDto.builder()
                .message(count + " limit was restored.")
                .build();
    }

    public InfoResponseDto test() {
        return InfoResponseDto.builder()
                .message("Service is up!")
                .build();
    }
}
