package com.sparta.ordermanagement.application.output;

import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import java.util.Optional;

public interface ShopCategoryOutputPort {

    String saveCategory(ShopCategoryForCreate categoryForCreate);

    Optional<ShopCategory> findByCategoryName(String categoryName);
}
