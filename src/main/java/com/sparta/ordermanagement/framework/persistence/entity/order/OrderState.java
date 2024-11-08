package com.sparta.ordermanagement.framework.persistence.entity.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {

    PENDING("대기"),
    APPROVED("승인"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String value;

}
