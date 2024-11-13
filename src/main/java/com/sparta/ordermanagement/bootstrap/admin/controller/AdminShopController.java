package com.sparta.ordermanagement.bootstrap.admin.controller;

import com.sparta.ordermanagement.application.admin.AdminShopService;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopListResponse;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/v1/shops")
@RestController
public class AdminShopController {

    private final AdminShopService adminShopService;

    @PaginationConstraint
    @GetMapping
    public Page<ShopListResponse> findAll(Pageable pageable) {

        return adminShopService.findAll(pageable)
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
}
