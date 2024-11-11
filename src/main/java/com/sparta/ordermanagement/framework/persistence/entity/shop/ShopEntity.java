package com.sparta.ordermanagement.framework.persistence.entity.shop;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_shop")
public class ShopEntity extends BaseEntity {

    @Id
    @Column(name = "shop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "uuid")
    private String shopUuid;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String userId;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String shopName;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double rating;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopCategoryEntity shopCategoryEntity;

    private ShopEntity(
        String userId, String shopName,
        ShopCategoryEntity shopCategoryEntity, String createdUserId) {

        super(createdUserId, createdUserId);
        this.userId = userId;
        this.shopName = shopName;
        this.rating = 0.0;
        this.shopCategoryEntity = shopCategoryEntity;
    }

    @PrePersist
    private void prePersistence() {
        shopUuid = UUID.randomUUID().toString();
    }

    public Shop toDomain() {
        ShopCategory shopCategory =
            new ShopCategory(shopCategoryEntity.getId(), shopCategoryEntity.getShopCategoryName());

        return new Shop(id, shopUuid, shopCategory, shopName, rating);
    }

    public static ShopEntity from(ShopForCreate shopForCreate) {
        ShopCategoryEntity shopCategoryEntity =
            ShopCategoryEntity.generateWithoutName(shopForCreate.shopCategoryId());

        return new ShopEntity(
            shopForCreate.ownerUserId(),
            shopForCreate.shopName(),
            shopCategoryEntity,
            shopForCreate.createdUserId());
    }

    public void updateFrom(ShopForUpdate shopForUpdate) {

        if (!isSameShopCategory(shopForUpdate.shopCategoryId())) {
            shopCategoryEntity = ShopCategoryEntity.generateWithoutName(shopForUpdate.shopCategoryId());
        }
        shopName = shopForUpdate.shopName();
        super.updateFrom(shopForUpdate.updateUserId());
    }

    private boolean isSameShopCategory(String categoryId) {
        return Objects.equals(shopCategoryEntity.getId(), categoryId);
    }
}
