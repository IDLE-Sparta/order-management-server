package com.sparta.ordermanagement.application.domain.review;

public record ReviewForUpdate(
    Integer rating,
    String content,
    String orderUuid,
    String reviewUuid,
    String userId
) {

}
