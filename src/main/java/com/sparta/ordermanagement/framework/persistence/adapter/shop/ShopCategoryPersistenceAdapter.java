package com.sparta.ordermanagement.framework.persistence.adapter.shop;

import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.application.output.shop.ShopCategoryOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopCategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShopCategoryPersistenceAdapter implements ShopCategoryOutputPort {

    private final ShopCategoryRepository shopCategoryRepository;

    @Override
    public String saveCategory(ShopCategoryForCreate categoryForCreate) {
        ShopCategoryEntity categoryEntity = ShopCategoryEntity.from(categoryForCreate);
        return shopCategoryRepository.save(categoryEntity).getId();
    }

    @Override
    public Optional<ShopCategory> findByCategoryName(String categoryName) {
        return shopCategoryRepository.findByShopCategoryName(categoryName)
            .map(ShopCategoryEntity::toDomain)
            .or(Optional::empty);
    }

    @Override
    public Optional<ShopCategory> findById(String shopCategoryId) {
        return shopCategoryRepository.findById(shopCategoryId)
            .map(ShopCategoryEntity::toDomain)
            .or(Optional::empty);
    }
}
