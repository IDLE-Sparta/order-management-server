package com.sparta.ordermanagement.framework.persistence.entity.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentState {

    PENDING("결제 대기"),
    COMPLETED("결제 완료"),
    CANCELED("결제 취소"),
    FAILED("결제 실패");

    private final String value;
}
