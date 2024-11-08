package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.application.service.ShopCategoryService;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopCategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/shops/categories")
@RestController
public class ShopCategoryCommandController {

    private final ShopCategoryService shopCategoryService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public String createShopCategory(@RequestBody ShopCategoryCreateRequest categoryCreateRequest) {
        ShopCategoryForCreate categoryForCreate = categoryCreateRequest.toDomain();
        return shopCategoryService.createCategory(categoryForCreate);
    }
}
