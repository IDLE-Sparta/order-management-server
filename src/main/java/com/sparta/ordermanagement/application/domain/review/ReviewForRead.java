package com.sparta.ordermanagement.application.domain.review;

public record ReviewForRead(
    String orderUuid,
    String reviewUuid,
    String userStringId
) {

}
