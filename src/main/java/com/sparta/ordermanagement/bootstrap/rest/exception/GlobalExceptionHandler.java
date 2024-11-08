package com.sparta.ordermanagement.bootstrap.rest.exception;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandle(Exception e) {
        log.error("exceptionHandle", e);

        return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
