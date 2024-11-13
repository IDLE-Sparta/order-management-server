package com.sparta.ordermanagement.application.domain.review;

public record ReviewForCreate(
    Integer rating,
    String content,
    String orderUuid,
    String userUuid
) {

}
