package com.sparta.ordermanagement.application.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopErrorMessage {

    CATEGORY_NAME_DUPLICATED("중복된 카테고리명 입니다.");

    private final String message;
}
