package com.sparta.ordermanagement.framework.persistence.adapter.shop;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.exception.InvalidValueException;
import com.sparta.ordermanagement.application.output.shop.ShopOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopQueryRepository;
import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopRepository;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ShopPersistenceAdapter implements ShopOutputPort {

    private final ShopRepository shopRepository;
    private final ShopQueryRepository shopQueryRepository;

    public Optional<Shop> findById(String shopId) {
        return shopRepository.findByShopUuid(shopId)
            .map(ShopEntity::toDomain)
            .or(Optional::empty);
    }

    public Optional<Shop> findByShopUuid(String shopUuid) {
        return shopRepository.findByShopUuid(shopUuid)
            .map(ShopEntity::toDomain)
            .or(Optional::empty);
    }

    public Optional<Shop> findByIdWithCategory(String shopId) {
        return shopRepository.findByIdWithCategory(shopId)
            .map(ShopEntity::toDomain)
            .or(Optional::empty);
    }

    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAllByDeletedIsFalse(pageable)
            .map(ShopEntity::toDomain);
    }

    public Page<Shop> findAllByCategoryId(String categoryId, Pageable pageable) {
        return shopRepository.findAllByCategoryId(categoryId, pageable)
            .map(ShopEntity::toDomain);
    }

    @Transactional
    @Override
    public String updateShop(ShopForUpdate shopForUpdate) {
        ShopEntity shopEntity = shopRepository.findById(shopForUpdate.shopId()).get();
        shopEntity.updateFrom(shopForUpdate);

        return shopEntity.getShopUuid();
    }

    public List<Shop> findAllByKeyword(String keyword, Cursor cursor) {
        return shopQueryRepository.findAllByKeyword(keyword, cursor)
            .stream().map(ShopEntity::toDomain).toList();
    }

    public String findByUserId(String userStringId) {
        return shopRepository.findByUserEntity_UserStringId(userStringId)
                .map(ShopEntity::getShopUuid)
                .orElseThrow(() -> new InvalidValueException("해당 유저의 가게 ID를 찾을 수 없습니다."));
    }
}
