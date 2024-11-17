package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {

    private String productUuid;
    private String productName;
    private Integer productPrice;
    private String productDescription;
    private ProductState productState;
    private String shopUuid;

    public static ProductDetailResponse from(Product product) {
        return new ProductDetailResponse(
            product.getProductUuid(),
            product.getProductName(),
            product.getProductPrice(),
            product.getProductDescription(),
            product.getProductState(),
            product.getShop().getUuid()
        );
    }
}
