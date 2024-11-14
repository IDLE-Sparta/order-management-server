package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.application.domain.product.ProductForDelete;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import com.sparta.ordermanagement.application.domain.product.ProductStateForUpdate;
import com.sparta.ordermanagement.application.exception.product.ProductDeletedException;
import com.sparta.ordermanagement.application.exception.product.ProductNotBelongToShopException;
import com.sparta.ordermanagement.application.exception.product.ProductUuidInvalidException;
import com.sparta.ordermanagement.application.output.ProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductOutputPort productOutputPort;
    private final ShopService shopService;

    public Product createProduct(ProductForCreate productForCreate) {
        shopService.validateNotDeletedShopUuid(productForCreate.shopUuid());
        return productOutputPort.saveProduct(productForCreate);
    }

    public Product updateProduct(ProductForUpdate productForUpdate) {

        shopService.validateNotDeletedShopUuid(productForUpdate.shopUuid());

        Product product = validateProductUuidAndGetProduct(productForUpdate.productUuid());
        validateProductBelongToShop(product, productForUpdate.shopUuid());
        validateProductIsNotDeleted(product);

        return productOutputPort.updateProduct(productForUpdate);
    }

    public Product updateProductState(ProductStateForUpdate productStateForUpdate) {

        shopService.validateNotDeletedShopUuid(productStateForUpdate.shopUuid());

        Product product = validateProductUuidAndGetProduct(productStateForUpdate.productUuid());
        validateProductBelongToShop(product, productStateForUpdate.shopUuid());
        validateProductIsNotDeleted(product);

        return productOutputPort.updateProductState(productStateForUpdate);
    }

    public Product deleteProduct(ProductForDelete productForDelete) {

        shopService.validateNotDeletedShopUuid(productForDelete.shopUuid());

        Product product = validateProductUuidAndGetProduct(productForDelete.productUuid());
        validateProductBelongToShop(product, productForDelete.shopUuid());
        validateProductIsNotDeleted(product);

        return productOutputPort.deleteProduct(productForDelete);
    }

    public Product validateProductUuidAndGetProduct(String productUuid) {
        return productOutputPort.findByProductUuid(productUuid)
            .orElseThrow(() -> new ProductUuidInvalidException(productUuid));
    }

    private void validateProductBelongToShop(Product product, String shopUuid) {
        if (!product.isSameShop(shopUuid)) {
            throw new ProductNotBelongToShopException(
                product.getProductUuid(),
                shopUuid
            );
        }
    }

    private void validateProductIsNotDeleted(Product product) {
        if (product.getIsDeleted()) {
            throw new ProductDeletedException(
                product.getProductUuid()
            );
        }
    }
}
