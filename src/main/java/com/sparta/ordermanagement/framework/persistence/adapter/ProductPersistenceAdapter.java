package com.sparta.ordermanagement.framework.persistence.adapter;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.application.domain.product.ProductForDelete;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import com.sparta.ordermanagement.application.domain.product.ProductStateForUpdate;
import com.sparta.ordermanagement.application.exception.product.ProductUuidInvalidException;
import com.sparta.ordermanagement.application.exception.shop.ShopUuidInvalidException;
import com.sparta.ordermanagement.application.output.ProductOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.repository.ProductQueryRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ProductRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ShopRepository;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductPersistenceAdapter implements ProductOutputPort {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ShopRepository shopRepository;

    public Optional<Product> findByProductUuid(String productUuid) {
        return productRepository.findByProductUuid(productUuid)
            .map(ProductEntity::toDomain)
            .or(Optional::empty);
    }

    public List<Product> findAllByShopUuid(String shopUuid, Cursor cursor) {
        return productQueryRepository.findAllByShopUuid(shopUuid, cursor)
            .stream()
            .map(ProductEntity::toDomain)
            .toList();
    }

    @Override
    public Product saveProduct(ProductForCreate productForCreate) {

        ShopEntity shopEntity = shopRepository.findByShopUuid(productForCreate.shopUuid())
            .orElseThrow(() -> new ShopUuidInvalidException(productForCreate.shopUuid()));

        return productRepository.save(ProductEntity.from(productForCreate, shopEntity)).toDomain();
    }

    @Transactional
    @Override
    public Product updateProduct(ProductForUpdate productForUpdate) {

        ProductEntity productEntity = getProductByUuid(productForUpdate.productUuid());
        productEntity.updateProduct(productForUpdate);

        return productEntity.toDomain();
    }

    @Transactional
    @Override
    public Product updateProductState(ProductStateForUpdate productStateForUpdate) {

        ProductEntity productEntity = getProductByUuid(productStateForUpdate.productUuid());
        productEntity.updateProductState(productStateForUpdate);

        return productEntity.toDomain();
    }

    @Transactional
    @Override
    public Product deleteProduct(ProductForDelete productForDelete) {

        ProductEntity productEntity = getProductByUuid(productForDelete.productUuid());
        productEntity.deleteProduct(productForDelete);

        return productEntity.toDomain();
    }

    private ProductEntity getProductByUuid(String productUuid) {
        return productRepository.findByProductUuid(productUuid)
            .orElseThrow(() -> new ProductUuidInvalidException(productUuid));
    }
}
