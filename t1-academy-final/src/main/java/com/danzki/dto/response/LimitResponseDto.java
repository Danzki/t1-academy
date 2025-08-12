package com.danzki.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ данные по лимиту")
public class LimitResponseDto {
    @Schema(description = "Поле ID клиента")
    private Long clientId;

    @Schema(description = "Поле сумма лимита")
    private Double currentValue;
}
