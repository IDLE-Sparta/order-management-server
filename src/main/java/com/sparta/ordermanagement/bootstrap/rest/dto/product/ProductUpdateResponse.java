package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductUpdateResponse {

    private final String productUuid;
    private final String shopUuid;

    public static ProductUpdateResponse from(Product product) {
        return new ProductUpdateResponse(
            product.getProductUuid(),
            product.getShop().getUuid()
        );
    }
}
