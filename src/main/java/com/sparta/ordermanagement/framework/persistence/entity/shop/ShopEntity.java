package com.sparta.ordermanagement.framework.persistence.entity.shop;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.admin.vo.ShopForCreate;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopUpdateRequest;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
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

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String shopName;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double rating;

    @Column(nullable = false)
    private int reviewCount;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopCategoryEntity shopCategoryEntity;

    private ShopEntity(
        UserEntity userEntity, String shopName,
        ShopCategoryEntity shopCategoryEntity, String createdUserId) {

        super(createdUserId, createdUserId);
        this.userEntity = userEntity;
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

        return new Shop(id, shopUuid, shopCategory, shopName, rating, super.isDeleted());
    }

    public static ShopEntity from(ShopForCreate shopForCreate, UserEntity userEntity) {
        ShopCategoryEntity shopCategoryEntity =
            ShopCategoryEntity.generateWithoutName(shopForCreate.shopCategoryId());

        return new ShopEntity(
            userEntity,
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

    public void updateFrom(ShopUpdateRequest shopUpdateRequest, UserEntity owner, String updateUserId) {
        if (!isSameShopCategory(shopUpdateRequest.getShopCategoryId())) {
            shopCategoryEntity = ShopCategoryEntity.generateWithoutName(
                shopUpdateRequest.getShopCategoryId());
        }
        shopName = shopUpdateRequest.getShopName();
        userEntity = owner;
        super.updateFrom(updateUserId);
    }

    public void deletedFrom(String deletedUserId) {
        super.deleteFrom(deletedUserId);
    }
}
