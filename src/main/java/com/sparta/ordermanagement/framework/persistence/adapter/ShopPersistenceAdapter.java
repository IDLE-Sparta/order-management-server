package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.admin.vo.ShopForCreate;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.output.ShopOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.repository.ShopQueryRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ShopRepository;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        return shopRepository.findAll(pageable)
            .map(ShopEntity::toDomain);
    }

    public Page<Shop> findAllByCategoryId(String categoryId, Pageable pageable) {
        return shopRepository.findAllByCategoryId(categoryId, pageable)
            .map(ShopEntity::toDomain);
    }

    @Override
    public String saveShop(ShopForCreate shopForCreate) {
        return shopRepository.save(ShopEntity.from(shopForCreate)).getShopUuid();
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
}
