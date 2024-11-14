package com.sparta.ordermanagement.framework.persistence.entity.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.application.domain.product.ProductForDelete;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import com.sparta.ordermanagement.application.domain.product.ProductStateForUpdate;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import jakarta.persistence.*;
import java.util.Optional;
import java.util.UUID;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "uuid", columnDefinition = "varchar(255)")
    private String productUuid;

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

    private ProductEntity(
        String productName,
        Integer productPrice,
        String productDescription,
        ProductState productState,
        ShopEntity shopEntity,
        String createUserId
    ) {
        super(createUserId, createUserId);
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productState = productState;
        this.shopEntity = shopEntity;
    }

    @PrePersist
    private void prePersistence() {
        productUuid = UUID.randomUUID().toString();
    }

    public static ProductEntity from(
        ProductForCreate productForCreate,
        ShopEntity shopEntity
    ) {
        return new ProductEntity(
            productForCreate.productName(),
            productForCreate.productPrice(),
            productForCreate.productDescription(),
            productForCreate.productState(),
            shopEntity,
            productForCreate.userId()
        );
    }

    public Product toDomain() {
        return new Product(
            id,
            productUuid,
            productName,
            productPrice,
            productDescription,
            productState,
            shopEntity.toDomain(),
            super.isDeleted()
        );
    }

    public void updateProduct(ProductForUpdate productForUpdate) {
        super.updateFrom(productForUpdate.userStringId());
        Optional.ofNullable(productForUpdate.productName()).ifPresent(value -> productName = value);
        Optional.ofNullable(productForUpdate.productPrice()).ifPresent(value -> productPrice = value);
        Optional.ofNullable(productForUpdate.productDescription()).ifPresent(value -> productDescription = value);
    }

    public void updateProductState(ProductStateForUpdate productStateForUpdate) {
        productState = productStateForUpdate.productState();
        super.updateFrom(productStateForUpdate.userId());
    }

    public void deleteProduct(ProductForDelete productForDelete) {
        super.deleteFrom(productForDelete.userId());
    }

    private ProductEntity(String productUuid) {
        this.productUuid = productUuid;
    }

    public static ProductEntity valueOf(String productId) {
        return new ProductEntity(productId);
    }
}
