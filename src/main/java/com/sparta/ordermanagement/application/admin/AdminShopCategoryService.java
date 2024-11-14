package com.sparta.ordermanagement.application.admin;

import com.sparta.ordermanagement.application.exception.shop.ShopCategoryIdInvalidException;
import com.sparta.ordermanagement.framework.admin.repository.AdminShopCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminShopCategoryService {

    private final AdminShopCategoryRepository shopCategoryRepository;

    public void validateShopCategoryId(String shopCategoryId) {

        shopCategoryRepository.findById(shopCategoryId)
            .orElseThrow(() -> new ShopCategoryIdInvalidException(shopCategoryId));
    }
}
