package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
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
    @DisplayName("[상품 수정 성공 테스트] OWNER 권한을 가지고 있는 사용자가 삭제되지 않는 가게의 삭제되지 않은 상품의 내용을 수정할 경우 상품 식별자와 가게 식별자를 반환한다.")
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
        Assertions.assertAll(
            () -> Assertions.assertNotNull(updatedProduct),
            () -> Assertions.assertEquals(updatedProduct.getProductUuid(), updatedAllProduct.getProductUuid()),
            () -> Assertions.assertEquals(updatedProduct.getShop().getUuid(), updatedAllProduct.getShop().getUuid()),
            () -> Assertions.assertEquals(updatedProduct.getProductName(), updatedAllProduct.getProductName()),
            () -> Assertions.assertEquals(updatedProduct.getProductPrice(), updatedAllProduct.getProductPrice()),
            () -> Assertions.assertEquals(updatedProduct.getProductDescription(), updatedAllProduct.getProductDescription())
        );

        Mockito.verify(productOutputPort, Mockito.times(1)).updateProduct(ArgumentMatchers.any(ProductForUpdate.class));
    }

    @Test
    @DisplayName("[상품 일부 수정 성공 테스트] OWNER 권한을 가지고 있는 사용자가 삭제되지 않는 가게의 삭제되지 않은 상품 내용의 일부를 수정할 경우 상품 식별자와 가게 식별자를 반환한다.")
    public void updatePartialFieldsProduct_successTest() {
        // Given
        ProductForUpdate productForUpdate = new ProductForUpdate(productName, 10_000,
            productDescription, testShopUuid, testExistProductUuid, testOwnerUser.getUserStringId(),
            testOwnerUser.getRole());

        Mockito.when(productOutputPort.findByProductUuid(ArgumentMatchers.eq(testExistProductUuid)))
            .thenReturn(Optional.of(existProduct));
        Mockito.when(productOutputPort.updateProduct(ArgumentMatchers.any(ProductForUpdate.class)))
            .thenReturn(updatedPartialProduct);

        Product updatedProduct = productService.updateProduct(productForUpdate);

        // When
        Assertions.assertAll(
            () -> Assertions.assertNotNull(updatedPartialProduct),
            () -> Assertions.assertEquals(updatedProduct.getProductUuid(), updatedPartialProduct.getProductUuid()),
            () -> Assertions.assertEquals(updatedProduct.getShop().getUuid(), updatedPartialProduct.getShop().getUuid()),
            () -> Assertions.assertEquals(updatedProduct.getProductName(), updatedPartialProduct.getProductName()),
            () -> Assertions.assertEquals(updatedProduct.getProductPrice(), updatedPartialProduct.getProductPrice()),
            () -> Assertions.assertEquals(updatedProduct.getProductDescription(), updatedPartialProduct.getProductDescription())
        );

        // Then
        Mockito.verify(productOutputPort, Mockito.times(1)).updateProduct(ArgumentMatchers.any(ProductForUpdate.class));
    }
}
