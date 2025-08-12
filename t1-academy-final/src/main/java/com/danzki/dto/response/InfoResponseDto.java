package com.danzki.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Ответ с информационным сообщением")
public class InfoResponseDto {
    @Schema(description = "Поле с текстом")
    private String message;
}
