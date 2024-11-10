package com.sparta.ordermanagement.framework.persistence.entity.shop;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopForCreate;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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
        String id, String userId, String shopName,
        ShopCategoryEntity shopCategoryEntity, String createdUserId) {

        super(createdUserId, createdUserId);
        this.id = id;
        this.userId = userId;
        this.shopName = shopName;
        this.rating = 0.0;
        this.shopCategoryEntity = shopCategoryEntity;
    }

    public Shop toDomain() {
        ShopCategory shopCategory =
            new ShopCategory(shopCategoryEntity.getId(), shopCategoryEntity.getShopCategoryName());

        return new Shop(id, shopCategory, shopName, rating);
    }

    public static ShopEntity from(ShopForCreate shopForCreate) {
        ShopCategoryEntity shopCategoryEntity =
            ShopCategoryEntity.generateWithoutName(shopForCreate.shopCategoryId());

        return new ShopEntity(
            null,
            shopForCreate.ownerUserId(),
            shopForCreate.shopName(),
            shopCategoryEntity,
            shopForCreate.createdUserId());
    }
}
