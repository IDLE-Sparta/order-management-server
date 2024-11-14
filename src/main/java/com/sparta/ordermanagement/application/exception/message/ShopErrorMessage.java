package com.sparta.ordermanagement.application.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopErrorMessage {

    CATEGORY_NAME_DUPLICATED("중복된 카테고리명 입니다."),
    CATEGORY_ID_INVALID("유효하지 않은 카테고리 식별자 입니다. : %s"),
    SHOP_ID_INVALID("유효하지 않은 가게 식별자 입니다. : %s"),
    SHOP_UUID_INVALID("유효하지 않은 가게 식별자 입니다. : %s"),
    SHOP_DELETED("삭제된 가게 식별자 입니다. : %s");

    private final String message;
}
