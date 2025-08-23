package com.danzki.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Сообщение об ошибке")
public class ErrorHandlerDto {
    @Schema(description = "Поле сообщение")
    private String message;

    @Schema(description = "Поле статус")
    private int status;

    @Schema(description = "Поле даты и времени ")
    private LocalDateTime timestamp;

    @Schema(description = "Поле с путем вызова")
    private String path;
}