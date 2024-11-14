package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewListResponse;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorPagination;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorRequest;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopListResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.ShopNotFoundException;
import com.sparta.ordermanagement.framework.persistence.adapter.ReviewPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.adapter.ShopPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import com.sparta.ordermanagement.framework.persistence.vo.ReviewSort;
import com.sparta.ordermanagement.framework.persistence.vo.ShopSort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops")
@RestController
public class ShopQueryController {

    private final ShopPersistenceAdapter shopPersistenceAdapter;
    private final ReviewPersistenceAdapter reviewPersistenceAdapter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shopId}")
    public ShopDetailResponse findOne(@PathVariable(value = "shopId") String shopId) {

        Shop shop = shopPersistenceAdapter.findByIdWithCategory(shopId)
            .orElseThrow(() -> new ShopNotFoundException(shopId));

        return ShopDetailResponse.from(shop);
    }

    @ResponseStatus(HttpStatus.OK)
    @PaginationConstraint
    @GetMapping
    public Page<ShopListResponse> findAll(Pageable pageable) {

        return shopPersistenceAdapter.findAll(pageable).map(ShopListResponse::from);
    }

    @ResponseStatus(HttpStatus.OK)
    @PaginationConstraint
    @GetMapping("/categories/{categoryId}")
    public Page<ShopListResponse> findAllByCategoryId(
         @PathVariable(name = "categoryId") String categoryId, Pageable pageable) {

        return shopPersistenceAdapter.findAllByCategoryId(categoryId, pageable)
            .map(ShopListResponse::from);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ShopListResponse> findAllByKeyword(
        @RequestParam(name = "keyword") String keyword,
        @CursorRequest CursorPagination cursorPagination) {

        ShopSort shopSort = ShopSort.of(cursorPagination.getSortedColumn());
        Cursor cursor = cursorPagination.toCursor(shopSort);

        return shopPersistenceAdapter.findAllByKeyword(keyword, cursor).stream()
            .map(ShopListResponse::from).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shopUuid}/reviews")
    public List<ReviewListResponse> findAllByShopUuid(
        @PathVariable(value = "shopUuid") String shopUuid,
        @CursorRequest CursorPagination cursorPagination
    ) {

        ReviewSort reviewSort = ReviewSort.of(cursorPagination.getSortedColumn());
        Cursor cursor = cursorPagination.toCursor(reviewSort);

        return reviewPersistenceAdapter.findAllByShopUuidAndIsDeletedFalse(shopUuid, cursor)
            .stream()
            .map(ReviewListResponse::from).toList();
    }
}
