package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopDetailResponse;
import com.sparta.ordermanagement.bootstrap.rest.exception.exceptions.ShopNotFoundException;
import com.sparta.ordermanagement.framework.persistence.adapter.ShopPersistenceAdapter;
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
@RequestMapping("/api/v1/shops")
@RestController
public class ShopQueryController {

    private final ShopPersistenceAdapter shopPersistenceAdapter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{shopId}")
    public ShopDetailResponse findOne(@PathVariable(value = "shopId") String shopId) {

        Shop shop = shopPersistenceAdapter.findByIdWithCategory(shopId)
            .orElseThrow(() -> new ShopNotFoundException(shopId));

        return ShopDetailResponse.from(shop);
    }
}
