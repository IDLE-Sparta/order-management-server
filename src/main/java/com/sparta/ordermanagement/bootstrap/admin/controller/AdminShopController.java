package com.sparta.ordermanagement.bootstrap.admin.controller;

import com.sparta.ordermanagement.application.admin.AdminShopService;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopCreateRequest;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopListResponse;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopSearchCondition;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopUpdateRequest;
import com.sparta.ordermanagement.bootstrap.auth.UserDetailsImpl;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationConstraint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "관리자 - 가게 관리", description = "가게 조회 수정 및 삭제 기능, MANAGER 이상의 권한 필요")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/v1/shops")
@RestController
public class AdminShopController {

    private final AdminShopService adminShopService;

    @Operation(summary = "모든 가게 조회", description = "키워드, 카테고리 기반 필터링, 정렬 기능 지원")
    @PaginationConstraint
    @GetMapping
    public Page<ShopListResponse> findAll(

        @Schema(description = "검색 키워드", example = "현수네")
        @RequestParam(name = "keyword", required = false) String keyword,

        @Schema(description = "가게 카테고리 Id", example = "example")
        @RequestParam(name = "categoryId", required = false) String categoryId,

        @Schema(description = "삭제된 가게 포함 여부 (기본 값: false)", example = "true")
        @RequestParam(name = "containingDeleted", required = false) boolean containingDeleted,

        @Schema(description = "페이징 정보", example = "page=1&size=10&sort=shopName,DESC")
        Pageable pageable
    ) {

        ShopSearchCondition condition = new ShopSearchCondition(keyword, categoryId, containingDeleted);

        return adminShopService.findAll(condition, pageable)
            .map(ShopListResponse::from);
    }

    @Operation(summary = "가게 생성", description = "Request Body 데이터를 기준으로 가게 데이터 저장")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createShop(
        @Valid @RequestBody ShopCreateRequest shopCreateRequest,
        @AuthenticationPrincipal UserDetailsImpl user
    ) {

        return adminShopService.createShop(shopCreateRequest.toDomain(user.getUserStringId()));
    }

    @Operation(summary = "가게 정보 수정", description = "가게 소유 유저, 가게명, 카테고리 변경")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{shopUuid}")
    public String updateShop(
        @Valid @RequestBody ShopUpdateRequest shopUpdateRequest,

        @Schema(description = "수정 대상 가게의 식별자(UUID)", example = "example UUID")
        @PathVariable(name = "shopUuid") String shopUuid,

        @AuthenticationPrincipal UserDetailsImpl user
    ) {

        return adminShopService.updateShop(shopUpdateRequest, shopUuid, user.getUserStringId());
    }

    @Operation(summary = "가게 삭제", description = "가게의 isDeleted 상태를 true 로 변경")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{shopUuid}")
    public String deleteShop(
        @Schema(description = "수정 대상 가게의 식별자(UUID)", example = "example UUID")
        @PathVariable(name = "shopUuid") String shopUuid,

        @AuthenticationPrincipal UserDetailsImpl user
    ) {

        return adminShopService.deleteShop(shopUuid, user.getUserStringId());
    }
}
