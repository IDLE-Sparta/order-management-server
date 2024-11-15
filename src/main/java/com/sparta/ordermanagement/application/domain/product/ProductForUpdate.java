package com.sparta.ordermanagement.application.domain.product;

import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

public record ProductForUpdate(
    String productName,
    Integer productPrice,
    String productDescription,
    String shopUuid,
    String productUuid,
    String userStringId,
    Role userRole
) {

}
