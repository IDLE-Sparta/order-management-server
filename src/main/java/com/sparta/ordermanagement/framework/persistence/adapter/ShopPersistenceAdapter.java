package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.repository.ShopRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShopPersistenceAdapter {

    private final ShopRepository shopRepository;

    public Optional<Shop> findById(String shopId) {
        return shopRepository.findById(shopId)
            .map(ShopEntity::toDomain)
            .or(Optional::empty);
    }

    public Optional<Shop> findByIdWithCategory(String shopId) {
        return shopRepository.findByIdWithCategory(shopId)
            .map(ShopEntity::toDomain)
            .or(Optional::empty);
    }

    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAll(pageable)
            .map(ShopEntity::toDomain);
    }
}
