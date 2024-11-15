package com.sparta.ordermanagement.application.domain.review;

public record ReviewForDelete(
    boolean deleteRequest,
    String orderUuid,
    String reviewUuid,
    String userStringId
) {

}
