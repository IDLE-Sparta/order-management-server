package com.sparta.ordermanagement.application.admin;

import com.sparta.ordermanagement.framework.admin.repository.AdminShopRepository;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminShopService {

    private final AdminShopRepository adminShopRepository;

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
}
