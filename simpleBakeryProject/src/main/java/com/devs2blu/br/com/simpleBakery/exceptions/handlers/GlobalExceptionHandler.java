package com.devs2blu.br.com.simpleBakery.exceptions.handlers;

import com.devs2blu.br.com.simpleBakery.exceptions.handlers.dto.ErrorDto;
import com.devs2blu.br.com.simpleBakery.exceptions.handlers.dto.FieldErrorDto;
import com.devs2blu.br.com.simpleBakery.utils.ErrorMessages;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ErrorMessages.VALIDATION_ERROR)
                        .status(HttpStatus.BAD_REQUEST.name())
                        .errors(
                                ex.getBindingResult().getFieldErrors().stream()
                                        .map(fieldError -> new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                                        .toList()
                        )
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException() {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ErrorMessages.INVALID_PATH_PARAMETER)
                        .status(HttpStatus.BAD_REQUEST.name())
                        .build()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.name())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .build()
        );
    }
}
