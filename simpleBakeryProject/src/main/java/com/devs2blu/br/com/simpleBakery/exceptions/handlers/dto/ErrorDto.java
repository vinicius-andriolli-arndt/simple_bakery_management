package com.devs2blu.br.com.simpleBakery.exceptions.handlers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ErrorDto {
    private final String message;
    private final String status;
    private final List<FieldErrorDto> errors;
}
