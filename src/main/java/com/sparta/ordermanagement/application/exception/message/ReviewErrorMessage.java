package com.sparta.ordermanagement.application.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorMessage {

    REVIEW_UUID_INVALID("유효하지 않은 리뷰 식별자 입니다.: %s"),
    REVIEW_DELETED("삭제된 리뷰입니다.: %s");

    private final String message;
}
