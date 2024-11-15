package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.ProductStateForUpdate;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductStateUpdateRequest {

    ProductState productState;

    public ProductStateForUpdate toDomain(
        String shopUuid,
        String productUuid,
        String userStringId,
        Role userRole
    ) {
        return new ProductStateForUpdate(
            productState,
            shopUuid,
            productUuid,
            userStringId,
            userRole
        );
    }
}
