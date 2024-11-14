package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateResponse {

    private final String productUuid;
    private final String shopUuid;

    public static ProductCreateResponse from(Product product) {
        return new ProductCreateResponse(
            product.getProductUuid(),
            product.getShop().getUuid()
        );
    }
}
