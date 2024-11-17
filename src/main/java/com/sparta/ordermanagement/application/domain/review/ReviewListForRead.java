package com.sparta.ordermanagement.application.domain.review;

import com.sparta.ordermanagement.framework.persistence.vo.Cursor;

public record ReviewListForRead(
    String shopUuid,
    Cursor cursor
) {

}
