package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.application.exception.shop.ShopCategoryIdInvalidException;
import com.sparta.ordermanagement.application.exception.shop.ShopCategoryNameDuplicatedException;
import com.sparta.ordermanagement.application.output.ShopCategoryOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopCategoryService {

    private final ShopCategoryOutputPort shopCategoryOutputPort;

    public String createCategory(ShopCategoryForCreate categoryForCreate) {
        validateCategoryNameDuplication(categoryForCreate.categoryName());
        return shopCategoryOutputPort.saveCategory(categoryForCreate);
    }

    private void validateCategoryNameDuplication(String categoryName) {
        shopCategoryOutputPort.findByCategoryName(categoryName).ifPresent(shopCategory -> {
            throw new ShopCategoryNameDuplicatedException();
        });
    }

    public void validateCategoryId(String shopCategoryId) {
        shopCategoryOutputPort.findById(shopCategoryId)
            .orElseThrow(() -> new ShopCategoryIdInvalidException(shopCategoryId));
    }
}
