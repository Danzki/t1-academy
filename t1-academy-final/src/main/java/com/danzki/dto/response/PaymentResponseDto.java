package com.danzki.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Ответ по информации о платеже")
public class PaymentResponseDto {
    @Schema(description = "Поле статус SUCCESS/ERROR")
    private String status;

    @Schema(description = "Поле текст сообщения в случае статуса SUCCESS")
    private String message;

    @Schema(description = "Поле текст ошибки в случае статуса ERROR")
    private String error;
}
