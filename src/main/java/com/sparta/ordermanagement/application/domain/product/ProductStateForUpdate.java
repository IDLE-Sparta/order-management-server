package com.sparta.ordermanagement.application.domain.product;

import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;

public record ProductStateForUpdate(
    ProductState productState,
    String shopUuid,
    String productUuid,
    String userId
) {

}
