package com.sparta.ordermanagement.application.domain.product;

import com.sparta.ordermanagement.framework.persistence.entity.user.Role;

public record ProductForDelete(
    boolean deleteRequest,
    String shopUuid,
    String productUuid,
    String userStringId,
    Role userRole
) {

}
