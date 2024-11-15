package com.sparta.ordermanagement.application.domain.product;

import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

public record ProductForCreate(
    String productName,
    Integer productPrice,
    String productDescription,
    ProductState productState,
    String shopUuid,
    String userStringId,
    Role userRole
) {

}
