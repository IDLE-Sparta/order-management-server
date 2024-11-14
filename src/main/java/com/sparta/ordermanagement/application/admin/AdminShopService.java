package com.sparta.ordermanagement.application.admin;

import com.sparta.ordermanagement.application.admin.vo.ShopForCreate;
import com.sparta.ordermanagement.application.exception.shop.ShopIdInvalidException;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopUpdateRequest;
import com.sparta.ordermanagement.framework.admin.repository.AdminShopRepository;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminShopService {

    private final AdminShopRepository adminShopRepository;
    private final AdminShopCategoryService shopCategoryService;

    public Page<ShopEntity> findAll(Pageable pageable) {

        return PageableSortProxy.executeWithFallback(pageable, adminShopRepository::findAll);
    }

    public Page<ShopEntity> finAllByKeyword(String keyword, Pageable pageable) {

        return PageableSortProxy.executeWithFallback(pageable, updatePageable ->
            adminShopRepository.findAllByShopNameContaining(keyword, updatePageable));
    }

    public Page<ShopEntity> findAllByCategoryId(String categoryId, Pageable pageable) {

        ShopCategoryEntity category = ShopCategoryEntity.generateWithoutName(categoryId);
        return PageableSortProxy.executeWithFallback(pageable, updatePageable ->
            adminShopRepository.findAllByShopCategoryEntity(category, updatePageable));
    }

    public String createShop(ShopForCreate shopForCreate) {

        shopCategoryService.validateShopCategoryId(shopForCreate.shopCategoryId());
        ShopEntity shopEntity = ShopEntity.from(shopForCreate);

        return adminShopRepository.save(shopEntity).getShopUuid();
    }

    @Transactional
    public String updateShop(
        ShopUpdateRequest shopUpdateRequest, String updatedShopUuid, String updatedUserUuid) {

        shopCategoryService.validateShopCategoryId(shopUpdateRequest.getShopCategoryId());

        ShopEntity shopEntity = adminShopRepository.findByShopUuid(updatedShopUuid)
            .orElseThrow(() -> new ShopIdInvalidException(updatedShopUuid));

        shopEntity.updateFrom(shopUpdateRequest, updatedUserUuid);
        return shopEntity.getShopUuid();
    }

    @Transactional
    public String deleteShop(String shopUuid, String deletedUserId) {
        ShopEntity shopEntity = adminShopRepository.findByShopUuid(shopUuid)
            .orElseThrow(() -> new ShopIdInvalidException(shopUuid));

        shopEntity.deletedFrom(deletedUserId);
        return shopEntity.getShopUuid();
    }
}
