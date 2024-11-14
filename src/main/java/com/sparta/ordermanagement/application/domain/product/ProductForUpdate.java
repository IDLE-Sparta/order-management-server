package com.sparta.ordermanagement.application.domain.product;

public record ProductForUpdate(
    String productName,
    Integer productPrice,
    String productDescription,
    String shopUuid,
    String productUuid,
    String userStringId
) {

}
