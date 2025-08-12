package com.danzki.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Запрос платежа")
public class PaymentRequestDto {
    @Schema(description = "Поле ID клиента")
    @NotNull(message = "Поле clientId не может быть пустым.")
    @Min(value = 1L, message = "Минимальное значение clientId = 1")
    @Max(value = 100L, message = "Максимальное значние clientId = 100")
    private Long clientId;

    @Schema(description = "Поле суммы платежа")
    @NotNull(message = "Сумма платежа не может быть пустой")
    @Positive(message = "Сумма платежа должна быть положительной")
    private Double payment;
}
