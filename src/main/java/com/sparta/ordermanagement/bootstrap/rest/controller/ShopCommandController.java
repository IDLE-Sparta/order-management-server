package com.sparta.ordermanagement.bootstrap.rest.controller;

import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.service.ShopService;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopCreateRequest;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shops")
public class ShopCommandController {

    // 삭제 예정: 추후 토큰으로부터 사용자 식별자를 받아와 대체할 것
    private static final String TEST_CREATED_USER_ID = "0000";

    private final ShopService shopService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createShop(@RequestBody ShopCreateRequest shopCreateRequest) {

        ShopForCreate shopForCreate = shopCreateRequest.toDomain(TEST_CREATED_USER_ID);

        return shopService.createShop(shopForCreate);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{shopId}")
    public String updateShop(
        @PathVariable(name = "shopId") String shopId,
        @RequestBody ShopUpdateRequest shopUpdateRequest) {

        ShopForUpdate shopForUpdate = shopUpdateRequest.toDomain(shopId, TEST_CREATED_USER_ID);

        return shopService.updateShop(shopForUpdate);
    }
}
