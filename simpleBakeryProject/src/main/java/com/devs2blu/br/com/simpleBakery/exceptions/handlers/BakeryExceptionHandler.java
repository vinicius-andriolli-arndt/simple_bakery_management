package com.devs2blu.br.com.simpleBakery.exceptions.handlers;

import com.devs2blu.br.com.simpleBakery.exceptions.BakeryNameAlreadyExists;
import com.devs2blu.br.com.simpleBakery.exceptions.handlers.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BakeryExceptionHandler {

    @ExceptionHandler(BakeryNameAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handlePublisherNameAlreadyExists(BakeryNameAlreadyExists ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .build()
        );
    }
}
