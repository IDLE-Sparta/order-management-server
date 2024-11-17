package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.application.service.ShopCategoryService;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopCategoryCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 - 카테고리 관리", description = "권한 수정 필요")
@RequiredArgsConstructor
@RequestMapping("/api/v1/shops/categories")
@RestController
public class ShopCategoryCommandController {

    private final ShopCategoryService shopCategoryService;

    @Operation(summary = "가게 카테고리 생성", description = "admin 이상의 권한으로 변경 예정")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createShopCategory(@RequestBody ShopCategoryCreateRequest categoryCreateRequest) {
        ShopCategoryForCreate categoryForCreate = categoryCreateRequest.toDomain();
        return shopCategoryService.createCategory(categoryForCreate);
    }
}
