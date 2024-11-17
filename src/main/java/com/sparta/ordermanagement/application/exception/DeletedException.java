package com.sparta.ordermanagement.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeletedException extends RuntimeException {

    public DeletedException(String message) {
        super(message);
    }
}
