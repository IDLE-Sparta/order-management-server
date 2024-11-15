package com.sparta.ordermanagement.application.service;

import com.sparta.ordermanagement.application.domain.orderproduct.OrderProductForCreate;
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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductOutputPort productOutputPort;
    private final UserService userService;
    private final ShopService shopService;

    public Product createProduct(ProductForCreate productForCreate) {

        userService.validateOwnerRole(productForCreate.userRole());

        shopService.validateNotDeletedShopUuid(productForCreate.shopUuid());

        return productOutputPort.saveProduct(productForCreate);
    }

    public Product updateProduct(ProductForUpdate productForUpdate) {

        userService.validateOwnerRole(productForUpdate.userRole());
        shopService.validateShopOwnership(productForUpdate.shopUuid(), productForUpdate.userStringId());
        validateProductState(productForUpdate.productUuid(), productForUpdate.shopUuid());

        return productOutputPort.updateProduct(productForUpdate);
    }

    public Product updateProductState(ProductStateForUpdate productStateForUpdate) {

        userService.validateOwnerRole(productStateForUpdate.userRole());
        shopService.validateShopOwnership(productStateForUpdate.shopUuid(), productStateForUpdate.userStringId());
        validateProductState(productStateForUpdate.productUuid(), productStateForUpdate.shopUuid());

        return productOutputPort.updateProductState(productStateForUpdate);
    }

    public Product deleteProduct(ProductForDelete productForDelete) {

        userService.validateOwnerRole(productForDelete.userRole());
        shopService.validateShopOwnership(productForDelete.shopUuid(), productForDelete.userStringId());
        validateProductState(productForDelete.productUuid(), productForDelete.shopUuid());

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

    public List<Product> validateProductsAndGetProductList(List<OrderProductForCreate> productList) {
        return productList.stream()
                .map(orderProduct -> productOutputPort.findByProductUuid(orderProduct.productId())
                        .orElseThrow(() -> new ProductUuidInvalidException(orderProduct.productId())))
                .collect(Collectors.toList());
    }

    private void validateProductState(String productUuid, String shopUuid) {
        Product product = validateProductUuidAndGetProduct(productUuid);
        validateProductBelongToShop(product, shopUuid);
        validateProductIsNotDeleted(product);
    }
}
