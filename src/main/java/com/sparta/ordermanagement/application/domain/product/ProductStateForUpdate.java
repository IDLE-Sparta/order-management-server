package com.sparta.ordermanagement.application.domain.product;

import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

public record ProductStateForUpdate(
    ProductState productState,
    String shopUuid,
    String productUuid,
    String userStringId,
    Role userRole
) {

}
