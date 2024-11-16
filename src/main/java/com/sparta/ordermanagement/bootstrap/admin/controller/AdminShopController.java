package com.sparta.ordermanagement.bootstrap.admin.controller;

import com.sparta.ordermanagement.application.admin.AdminShopService;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopCreateRequest;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopListResponse;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopSearchCondition;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopUpdateRequest;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/v1/shops")
@RestController
public class AdminShopController {

    private final AdminShopService adminShopService;

    @PaginationConstraint
    @GetMapping
    public Page<ShopListResponse> findAll(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "categoryId", required = false) String categoryId,
        @RequestParam(name = "containingDeleted", required = false) boolean containingDeleted,
        Pageable pageable) {

        ShopSearchCondition condition = new ShopSearchCondition(keyword, categoryId, containingDeleted);

        return adminShopService.findAll(condition, pageable)
            .map(ShopListResponse::from);
    }

    @PaginationConstraint
    @GetMapping("/search")
    public Page<ShopListResponse> findAllByKeyword(
        @RequestParam(name = "keyword") String keyword,
        Pageable pageable) {

        return adminShopService.finAllByKeyword(keyword, pageable)
            .map(ShopListResponse::from);
    }

    @PaginationConstraint
    @GetMapping("/categories/{categoryId}")
    public Page<ShopListResponse> findAllByCategoryId(
        @PathVariable(name = "categoryId") String categoryId,
        Pageable pageable) {

        return adminShopService.findAllByCategoryId(categoryId, pageable)
            .map(ShopListResponse::from);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createShop(
        @Valid @RequestBody ShopCreateRequest shopCreateRequest,
        @AuthenticationPrincipal UserDetailsImpl user) {

        return adminShopService.createShop(shopCreateRequest.toDomain(user.getUserStringId()));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{shopUuid}")
    public String updateShop(
        @Valid @RequestBody ShopUpdateRequest shopUpdateRequest,
        @PathVariable(name = "shopUuid") String shopUuid,
        @AuthenticationPrincipal UserDetailsImpl user) {

        return adminShopService.updateShop(shopUpdateRequest, shopUuid, user.getUserStringId());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{shopUuid}")
    public String deleteShop(
        @PathVariable(name = "shopUuid") String shopUuid,
        @AuthenticationPrincipal UserDetailsImpl user) {

        return adminShopService.deleteShop(shopUuid, user.getUserStringId());
    }
}
