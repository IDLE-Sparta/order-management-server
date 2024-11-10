package com.sparta.ordermanagement.bootstrap.rest.exception;

import com.sparta.ordermanagement.application.exception.ConstraintException;
import com.sparta.ordermanagement.application.exception.InvalidValueException;
import com.sparta.ordermanagement.bootstrap.rest.exception.response.ApiResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.response.ApiResponse.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandle(Exception e) {
        log.error("exceptionHandle", e);

        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
