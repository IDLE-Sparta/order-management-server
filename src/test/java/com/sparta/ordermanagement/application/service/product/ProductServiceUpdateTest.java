package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUpdateTest extends BaseProductServiceTest {

    private String productName;
    private Integer price;
    private String productDescription;

    private Product updatedAllProduct;
    private Product updatedPartialProduct;

    @BeforeEach
    void setUp() {
        super.setUp();
        productName = "간장 치킨";
        price = 19_000;
        productDescription = "맛있는 간장치킨";

        updatedAllProduct = createProduct(1L, testExistProductUuid, productName, price);
        updatedPartialProduct = createProduct(1L, testExistProductUuid, productName, 10_000);
    }

    @Test
    @DisplayName("[상품 수정 성공 테스트] OWNER 권한으로 상품 모든 필드를 수정하면 수정된 상품 반환한다.")
    public void updateAllFieldsProduct_successTest() {
        // Given
        ProductForUpdate productForUpdate = new ProductForUpdate(productName, price,
            productDescription, testShopUuid, testExistProductUuid, testOwnerUser.getUserStringId(),
            testOwnerUser.getRole());

        Mockito.when(productOutputPort.findByProductUuid(ArgumentMatchers.eq(testExistProductUuid)))
               .thenReturn(Optional.of(existProduct));
        Mockito.when(productOutputPort.updateProduct(ArgumentMatchers.any(ProductForUpdate.class)))
                .thenReturn(updatedAllProduct);

        // When
        Product updatedProduct = productService.updateProduct(productForUpdate);

        // Then
        assertProductFields(updatedAllProduct, updatedProduct);

        Mockito.verify(productOutputPort, Mockito.times(1)).updateProduct(ArgumentMatchers.any(ProductForUpdate.class));
    }

    @Test
    @DisplayName("[상품 일부 수정 성공 테스트] OWNER 권한으로 상품 일부 필드를 수정하면 수정된 상품 반환한다.")
    public void updatePartialFieldsProduct_successTest() {
        // Given
        ProductForUpdate productForUpdate = new ProductForUpdate(productName, 10_000,
            productDescription, testShopUuid, testExistProductUuid, testOwnerUser.getUserStringId(),
            testOwnerUser.getRole());

        Mockito.when(productOutputPort.findByProductUuid(ArgumentMatchers.eq(testExistProductUuid)))
            .thenReturn(Optional.of(existProduct));
        Mockito.when(productOutputPort.updateProduct(ArgumentMatchers.any(ProductForUpdate.class)))
            .thenReturn(updatedPartialProduct);

        // When
        Product updatedProduct = productService.updateProduct(productForUpdate);

        // Then
        assertProductFields(updatedPartialProduct, updatedProduct);

        Mockito.verify(productOutputPort, Mockito.times(1)).updateProduct(ArgumentMatchers.any(ProductForUpdate.class));
    }

    private void assertProductFields(Product expectedProduct, Product actualProduct) {
        Assertions.assertAll(
            "Product 필드 검증",
            () -> Assertions.assertNotNull(actualProduct, "실제 Product가 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductUuid(), actualProduct.getProductUuid(), "Product 식별자가 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getShop().getUuid(), actualProduct.getShop().getUuid(), "Shop 식별자가 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductName(), actualProduct.getProductName(), "Product 이름이 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductPrice(), actualProduct.getProductPrice(), "Product 가격이 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductDescription(), actualProduct.getProductDescription(), "Product 설명이 일치하지 않습니다.")
        );
    }
}
