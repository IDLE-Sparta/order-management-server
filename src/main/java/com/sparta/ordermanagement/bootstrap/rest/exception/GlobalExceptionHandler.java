package com.sparta.ordermanagement.bootstrap.rest.exception;

import com.sparta.ordermanagement.application.exception.ConstraintException;
import com.sparta.ordermanagement.application.exception.DeletedException;
import com.sparta.ordermanagement.application.exception.ForbiddenException;
import com.sparta.ordermanagement.application.exception.InvalidValueException;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.RequestValidationException;
import com.sparta.ordermanagement.bootstrap.rest.exception.response.ApiResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.response.ApiResponse.Error;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintException.class)
    public ResponseEntity<Error> exceptionHandle(ConstraintException e) {
        log.error("constraintExceptionHandle", e);

        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Error> exceptionHandle(InvalidValueException e) {
        log.error("invalidValueExceptionHandle", e);

        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Error> exceptionHandle(ForbiddenException e) {
        log.error("forbiddenExceptionHandle", e);

        return ApiResponse.error(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> exceptionHandle(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandle", e);

        String errorMessage = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));

        return ApiResponse.error(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandle(Exception e) {
        log.error("exceptionHandle", e);

        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<Error> exceptionHandle(RequestValidationException e) {
        log.error("RequestValidationExceptionHandle", e);

        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DeletedException.class)
    public ResponseEntity<Error> exceptionHandle(DeletedException e) {
        log.error("deletedExceptionHandle", e);

        return ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
