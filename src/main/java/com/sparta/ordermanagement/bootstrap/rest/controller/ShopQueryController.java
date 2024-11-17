package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewListForRead;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.service.ReviewService;
import com.sparta.ordermanagement.bootstrap.rest.dto.review.ReviewListResponse;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorPagination;
import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorRequest;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopListResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.ShopNotFoundException;
import com.sparta.ordermanagement.framework.persistence.adapter.ShopPersistenceAdapter;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import com.sparta.ordermanagement.framework.persistence.vo.ReviewSort;
import com.sparta.ordermanagement.framework.persistence.vo.ShopSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "사용자 - 가게 조회", description = "권한 불필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops")
@RestController
public class ShopQueryController {

    private final ShopPersistenceAdapter shopPersistenceAdapter;
    private final ReviewService reviewService;

    @Operation(summary = "특정 가게 상세 정보 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shopId}")
    public ShopDetailResponse findOne(

        @Schema(description = "조회 대상 가게의 식별자(UUID)", example = "example UUID")
        @PathVariable(value = "shopId") String shopId) {

        Shop shop = shopPersistenceAdapter.findByIdWithCategory(shopId)
            .orElseThrow(() -> new ShopNotFoundException(shopId));

        return ShopDetailResponse.from(shop);
    }

    @Operation(summary = "전체 가게 조회")
    @ResponseStatus(HttpStatus.OK)
    @PaginationConstraint
    @GetMapping
    public Page<ShopListResponse> findAll(Pageable pageable) {

        return shopPersistenceAdapter.findAll(pageable).map(ShopListResponse::from);
    }

    @Operation(summary = "특정 카테고리의 가게 리스트 조회")
    @ResponseStatus(HttpStatus.OK)
    @PaginationConstraint
    @GetMapping("/categories/{categoryId}")
    public Page<ShopListResponse> findAllByCategoryId(
         @PathVariable(name = "categoryId") String categoryId, Pageable pageable) {

        return shopPersistenceAdapter.findAllByCategoryId(categoryId, pageable)
            .map(ShopListResponse::from);
    }

    @Operation(summary = "특정 키워드를 포함한 가게 리스트 조회", description = "커서 기반 페이지네이션")
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

    @Operation(summary = "특정 가게의 전체 리뷰 조회", description = "커서 기반 페이지네이션")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shopUuid}/reviews")
    public ReviewListResponse.GetReviewsResponse findAllByShopUuid(
        @PathVariable(value = "shopUuid") String shopUuid,
        @CursorRequest CursorPagination cursorPagination
    ) {

        ReviewSort reviewSort = ReviewSort.of(cursorPagination.getSortedColumn());
        Cursor cursor = cursorPagination.toCursor(reviewSort);

        ReviewListForRead reviewListForRead = new ReviewListForRead(
            shopUuid,
            cursor
        );
        List<Review> reviews = reviewService.getReviews(reviewListForRead);

        return ReviewListResponse.GetReviewsResponse.of(reviews);
    }
}
