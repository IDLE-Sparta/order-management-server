package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.bootstrap.rest.dto.product.ProductListResponse;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorPagination;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorRequest;
import com.sparta.ordermanagement.framework.persistence.adapter.ProductPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import com.sparta.ordermanagement.framework.persistence.vo.ProductSort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductListResponse> findAllByShopUuid(
        @PathVariable(value = "shopUuid") String shopUuid,
        @CursorRequest CursorPagination cursorPagination
    ) {

        // TODO : 상품 조회 시 삭제된 상품이나 HIDE 처리된 상품은 안보여주게 처리

        ProductSort productSort = ProductSort.of(cursorPagination.getSortedColumn());
        Cursor cursor = cursorPagination.toCursor(productSort);

        return productPersistenceAdapter.findAllByShopUuid(shopUuid, cursor)
            .stream()
            .map(ProductListResponse::from)
            .collect(Collectors.toList());
    }
}
