package com.sparta.ordermanagement.framework.persistence.entity.product;

import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_product")
public class ProductEntity extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Column(name = "product_name", nullable = false, columnDefinition = "varchar(255)")
    private String productName;

    @Column(name = "product_price", nullable = false, columnDefinition = "varchar(255)")
    private Integer productPrice;

    @Column(name = "product_description", nullable = false, columnDefinition = "varchar(255)")
    private String productDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private ProductState productState;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shopEntity;
}
