package com.sparta.ordermanagement.framework.persistence.entity.shop;

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

}
