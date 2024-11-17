package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.service.ProductService;
import com.sparta.ordermanagement.application.service.ShopService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.product.ProductDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.product.ProductListResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.ProductNotFoundException;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorPagination;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorRequest;
import com.sparta.ordermanagement.framework.persistence.adapter.ProductPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import com.sparta.ordermanagement.framework.persistence.vo.ProductSort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops/{shopUuid}/products")
@RestController
public class ProductQueryController {

    private final ProductPersistenceAdapter productPersistenceAdapter;
    private final ProductService productService;
    private final ShopService shopService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{productUuid}")
    public ProductDetailResponse findByProductUuid(
        @PathVariable(value = "shopUuid") String shopUuid,
        @PathVariable(value = "productUuid") String productUuid,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        // TODO : 로그인 안해도 볼 수 있게 로그인 토큰 제거하기

        shopService.validateShopOwner(shopUuid, userDetails.getUserStringId());

        Product product = productPersistenceAdapter.findByProductUuid(productUuid)
            .orElseThrow(() -> new ProductNotFoundException(productUuid));

        return ProductDetailResponse.from(
            productService.validateProductNotDeletedAndGetProduct(product)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ProductListResponse.GetProductsResponse findAllByShopUuid(
        @PathVariable(value = "shopUuid") String shopUuid,
        @CursorRequest CursorPagination cursorPagination
    ) {

        ProductSort productSort = ProductSort.of(cursorPagination.getSortedColumn());
        Cursor cursor = cursorPagination.toCursor(productSort);

        shopService.validateNotDeletedShopUuid(shopUuid);
        List<Product> products = productPersistenceAdapter.findAllByShopUuidAndIsDeletedFalseAndProductStateShow(shopUuid, cursor);

        return ProductListResponse.GetProductsResponse.of(products);
    }
}
