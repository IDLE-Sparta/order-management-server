package com.sparta.ordermanagement.framework.persistence.entity.shop;

import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_shop_category")
public class ShopCategoryEntity extends BaseEntity {

    @Id
    @Column(name = "shop_category_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String shopCategoryName;

    private ShopCategoryEntity(String id) {
        this.id = id;
    }

    public static ShopCategoryEntity from(ShopCategoryForCreate shopCategoryForCreate) {
        return new ShopCategoryEntity(null, shopCategoryForCreate.categoryName());
    }

    public static ShopCategoryEntity generateWithoutName(String id) {
        return new ShopCategoryEntity(id);
    }

    public ShopCategory toDomain() {
        return new ShopCategory(id, shopCategoryName);
    }
}
