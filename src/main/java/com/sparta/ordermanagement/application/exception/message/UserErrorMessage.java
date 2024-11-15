package com.sparta.ordermanagement.application.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorMessage {

    USER_ACCESS_DENIED("접근 권한이 없습니다.: %s");

    private final String message;
}
