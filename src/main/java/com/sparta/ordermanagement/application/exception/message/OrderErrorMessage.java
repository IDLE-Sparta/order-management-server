package com.sparta.ordermanagement.application.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorMessage {

    USER_ID_INVALID("유효하지 않은 유저 식별자 입니다. : %s"),
    ORDER_PRODUCT_ID_INVALID("유효하지 않은 주문 상품 식별자 입니다. : %s"),
    ORDER_ID_INVALID("유효하지 않은 주문 식별자 입니다. : %s"),
    ORDER_CANCELLATION_TIME_EXCEEDED("주문 : %s는 주문 후 5분이 지나 취소할 수 없습니다."),
    ORDER_STATE_CANNOT_DB_CHANGED("주문 : %s 의 주문 상태를 변경할 수 없습니다."),
    ORDER_STATE_INVALID("유효하지 않은 주문 상태 입니다."),
    ORDER_UUID_INVALID("유효하지 않은 주문 식별자 입니다. : %s"),
    ORDER_DELETED("삭제된 주문 식별자 입니다. : %s"),
    UNAUTHORIZED_ACCESS("유효하지 않은 권한의 유저 입니다."),
    ORDER_MISMATCH_REVIEWER("주문자 정보가 일치하지 않습니다.: %s");

    private final String message;
}
