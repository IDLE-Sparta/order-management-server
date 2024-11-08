package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String getMessage();

    HttpStatus getHttpStatus();
}
