package com.sparta.ordermanagement.framework.persistence.entity.shop;

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
    private String shopCategoryId;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String shopName;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double rating = 0.0;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopCategoryEntity shopCategoryEntity;
}
