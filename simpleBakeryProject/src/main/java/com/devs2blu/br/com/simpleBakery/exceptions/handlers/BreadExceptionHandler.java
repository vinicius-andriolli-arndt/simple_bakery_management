package com.devs2blu.br.com.simpleBakery.exceptions.handlers;

import com.devs2blu.br.com.simpleBakery.exceptions.IfsAlreadyExistsException;
import com.devs2blu.br.com.simpleBakery.exceptions.PublicationYearTooNew;
import com.devs2blu.br.com.simpleBakery.exceptions.handlers.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BreadExceptionHandler {

    @ExceptionHandler(IfsAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleIsbnAlreadyExists(IfsAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .build()
        );
    }

    @ExceptionHandler(PublicationYearTooNew.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handlePublicationYearTooNew(PublicationYearTooNew ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .build()
        );
    }
}
