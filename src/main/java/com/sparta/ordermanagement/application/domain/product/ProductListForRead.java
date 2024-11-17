package com.sparta.ordermanagement.application.domain.product;

import com.sparta.ordermanagement.framework.persistence.vo.Cursor;

public record ProductListForRead(
    String shopUuid,
    Cursor cursor
) {

}
