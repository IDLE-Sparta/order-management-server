package com.sparta.ordermanagement.application.domain.product;

public record ProductForDelete (
    boolean deleteRequest,
    String shopUuid,
    String productUuid,
    String userId
) {

}
