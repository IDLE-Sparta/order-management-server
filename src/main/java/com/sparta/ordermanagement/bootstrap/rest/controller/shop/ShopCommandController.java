package com.sparta.ordermanagement.bootstrap.rest.controller.shop;

import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.service.shop.ShopService;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.dto.shop.ShopUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 - 가게 관리", description = "OWNER 이상의 권한 필요")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shops")
public class ShopCommandController {

    private final ShopService shopService;

    @Operation(summary = "본인 소유의 가게 정보 변경", description = "가게 카테고리, 명칭 변경 가능")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{shopId}")
    public String updateShop(
        @Schema(description = "수정 대상 가게의 식별자(UUID)", example = "example UUID")
        @PathVariable(name = "shopId") String shopId,
        @RequestBody ShopUpdateRequest shopUpdateRequest,
        @AuthenticationPrincipal UserDetailsImpl user) {

        ShopForUpdate shopForUpdate = shopUpdateRequest.toDomain(shopId, user.getUserStringId());

        return shopService.updateShop(shopForUpdate);
    }
}
