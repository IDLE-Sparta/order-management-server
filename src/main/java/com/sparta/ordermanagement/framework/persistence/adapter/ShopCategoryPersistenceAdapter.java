package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.application.output.ShopCategoryOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.repository.ShopCategoryRepository;
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
}
