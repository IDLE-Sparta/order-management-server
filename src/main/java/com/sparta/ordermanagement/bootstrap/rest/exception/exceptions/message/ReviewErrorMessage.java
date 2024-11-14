package com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorMessage {

    REVIEW_NOT_FOUND("가게에 존재하지 않는 리뷰입니다. shopUuid: %s reviewUuid: %s");

    private final String message;
}
