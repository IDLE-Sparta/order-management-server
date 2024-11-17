package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import java.util.List;
import java.util.stream.Collectors;

public record ProductListResponse(
    String productUuid,
    String productName,
    Integer productPrice,
    String productDescription
) {

    public static ProductListResponse from(Product product) {
        return new ProductListResponse(
            product.getProductUuid(),
            product.getProductName(),
            product.getProductPrice(),
            product.getProductDescription()
        );
    }

    public static List<ProductListResponse> from(List<Product> products) {
        return products.stream().map(ProductListResponse::from).collect(Collectors.toList());
    }

    public record GetProductsResponse(
        List<ProductListResponse> products
    ) {
        public static GetProductsResponse of(List<Product> products) {
            return new GetProductsResponse(ProductListResponse.from(products));
        }
    }
}
