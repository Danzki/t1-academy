package com.danzki.controller;

import com.danzki.dto.request.PaymentRequestDto;
import com.danzki.dto.response.InfoResponseDto;
import com.danzki.dto.response.LimitResponseDto;
import com.danzki.dto.response.PaymentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Limits controller", description = "This is interface for daily limits management")
@RequestMapping("/limits")
public interface LimitController {

    @Operation(method = "getById", summary = "Method to get limit by id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Limit ID",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "123"
                    )},
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Limit found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LimitResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"clientId\"=\"123\", \"currentLimit\"=\"10000\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Limit not found"
                    )
            }
    )
    @GetMapping("/get/{id}")
    ResponseEntity<LimitResponseDto> getById(@PathVariable Long id);

    @Operation(method = "getByClientId", summary = "Method to get limit by client id",
            parameters = {
                    @Parameter(
                            name = "clientId",
                            description = "Client ID",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "123"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Limit found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LimitResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"clientId\": 123, \"currentLimit\": 10000.00}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Limit not found"
                    )
            }
    )
    @GetMapping("/get/client/{id}")
    ResponseEntity<LimitResponseDto> getByClientId(@PathVariable Long id);

//    @PostMapping("/add")
//    @Operation(method = "add", summary = "Method to new limit",
//            requestBody = @RequestBody(
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = PaymentRequestDto.class),
//                            examples = {
//                                    @ExampleObject(
//                                            value = "{\"clientId\"=\"123\", \"currentLimit\"=\"10000\"}"
//                                    )
//                            }
//                    )
//            ),
//            responses = {
//                @ApiResponse(
//                        responseCode = "200", description = "New limit parameters",
//                        content = @Content(
//                                mediaType = "application/json",
//                                schema = @Schema(implementation = LimitResponseDto.class),
//                                examples = {
//                                        @ExampleObject(
//                                                value = "{\"clientId\"=\"123\", \"currentLimit\"=\"10000\"}"
//                                        )
//                                }
//                        )
//                )
//            }
//    )
//    ResponseEntity<LimitResponseDto> add(@RequestBody PaymentRequestDto dto);

    @Operation(method = "deleteByClientId", summary = "Method to delete limit by client id",
            parameters = {
                    @Parameter(
                            name = "clientId",
                            description = "Client ID",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "123"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "New limit parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LimitResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"clientId\"=\"123\", \"currentLimit\"=\"10000\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @DeleteMapping("/delete/client/{id}")
    ResponseEntity<InfoResponseDto> deleteByClientId(@PathVariable Long id);

    @Operation(method = "paymentApply", summary = "Method to apply payment to limit, if limit was not found by client id, it will be added with default current amount",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\"clientId\": 123, \"payment\": 5000.00}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Payment processed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaymentResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"status\"=\"SUCCESS\", \"message\"=\"New limit is 5000\", \"error\"=\"\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Payment failed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaymentResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"status\"=\"ERROR\", \"message\"=\"\", \"error\"=\"Payment failed\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/payment/apply")
    ResponseEntity<PaymentResponseDto> paymentApply(@RequestBody @Valid PaymentRequestDto dto);

    @Operation(method = "paymentRevert", summary = "Method to revert payment to limit",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\"clientId\": 123, \"payment\": 5000.00}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Payment revert processed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaymentResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"status\"=\"SUCCESS\", \"message\"=\"Payment reverted. New limit is 5000\", \"error\"=\"\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Payment revert failed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaymentResponseDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"status\"=\"ERROR\", \"message\"=\"\", \"error\"=\"Payment revert failed\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/payment/revert")
    ResponseEntity<PaymentResponseDto> paymentRevert(@RequestBody @Valid PaymentRequestDto dto);


    @PostMapping("/test/restore")
    ResponseEntity<InfoResponseDto> restoreAllLimits();

    @GetMapping("/test")
    ResponseEntity<InfoResponseDto> test();
}
