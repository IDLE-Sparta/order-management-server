package com.sparta.ordermanagement.framework.persistence.entity.product;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductState {

    HIDE("숨김 처리"),
    SHOW("공개 처리");

    private final String value;
}
