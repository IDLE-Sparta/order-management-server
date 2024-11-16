package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.exception.product.ProductDeletedException;
import com.sparta.ordermanagement.application.exception.shop.ShopOwnerMismatchException;
import com.sparta.ordermanagement.application.exception.user.UserAccessDeniedException;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
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

        Mockito.verify(productOutputPort, Mockito.times(1))
            .updateProduct(ArgumentMatchers.any(ProductForUpdate.class));
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

        Mockito.verify(productOutputPort, Mockito.times(1))
            .updateProduct(ArgumentMatchers.any(ProductForUpdate.class));
    }

    private void assertProductFields(Product expectedProduct, Product actualProduct) {
        Assertions.assertAll(
            "Product 필드 검증",
            () -> Assertions.assertNotNull(actualProduct, "실제 Product가 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductUuid(),
                actualProduct.getProductUuid(), "Product 식별자가 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getShop().getUuid(),
                actualProduct.getShop().getUuid(), "Shop 식별자가 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductName(),
                actualProduct.getProductName(), "Product 이름이 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductPrice(),
                actualProduct.getProductPrice(), "Product 가격이 일치하지 않습니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductDescription(),
                actualProduct.getProductDescription(), "Product 설명이 일치하지 않습니다.")
        );
    }

    @Test
    @DisplayName("[상품 수정 실패 테스트] OWNER 권한이 없는 사용자가 상품 필드를 수정하면 예외를 발생시킨다.")
    public void updateProduct_failureTest_notOwnerRole() {
        // Given
        ProductForUpdate productForUpdate = new ProductForUpdate(productName, price,
            productDescription, testShopUuid, testExistProductUuid,
            testCustomerUser.getUserStringId(),
            testCustomerUser.getRole());

        Mockito.doThrow(new UserAccessDeniedException(testCustomerUser.getRole()))
            .when(userService)
            .validateOwnerRole(ArgumentMatchers.argThat(role -> role != Role.OWNER));

        // When & Then
        UserAccessDeniedException exception = Assertions.assertThrows(
            UserAccessDeniedException.class,
            () -> productService.updateProduct(productForUpdate)
        );

        Assertions.assertEquals(
            String.format("접근 권한이 없습니다.: %s", testCustomerUser.getRole()),
            exception.getMessage()
        );

        Mockito.verifyNoInteractions(shopService, productOutputPort);
    }

    @Test
    @DisplayName("[상품 수정 실패 테스트] OWNER 권한을 가진 사용자가 자신이 소유한 가게가 아닌 상품 수정할 경우 예외를 발생시킨다.")
    public void updateProduct_failureTest_notShopOwner() {
        // Given
        User otherOwner = createUser(3L, "owner2", Role.OWNER);
        Shop otherShop = new Shop(1L, "other-shop-uuid", testShopCategory, "소현이네 bbq", 4.0,
            otherOwner.getUserStringId());
        Product product = new Product(3L, "other-shop-product-uuuid", "황금올리브", 23_000, "맛있는 황금올리브",
            ProductState.SHOW, otherShop, false);

        ProductForUpdate productForUpdate = new ProductForUpdate(productName, price,
            productDescription, testShopUuid, product.getProductUuid(),
            testOwnerUser.getUserStringId(),
            testOwnerUser.getRole());

        Mockito.doThrow(new ShopOwnerMismatchException(testOwnerUser.getUserStringId()))
            .when(shopService)
            .validateShopOwner(testShopUuid, testOwnerUser.getUserStringId());

        // When & Then
        ShopOwnerMismatchException exception = Assertions.assertThrows(
            ShopOwnerMismatchException.class,
            () -> productService.updateProduct(productForUpdate)
        );

        Assertions.assertEquals(
            String.format("가게 소유자 정보가 일치하지 않습니다. : %s", testOwnerUser.getUserStringId()),
            exception.getMessage()
        );

        Mockito.verifyNoInteractions(productOutputPort);
    }

    @Test
    @DisplayName("[상품 수정 실패 테스트] OWNER 권한을 가진 사용자가 자신의 가게에 속한 삭제된 상품을 수정하려고 할 때 예외를 발생시킨다.")
    public void updateProduct_failureTest_deletedProduct() {
        // Given
        Product product = new Product(1L, existProduct.getProductUuid(),
            existProduct.getProductName(), existProduct.getProductPrice(),
            existProduct.getProductDescription(),
            ProductState.SHOW, testShop, true);

        ProductForUpdate productForUpdate = new ProductForUpdate(productName, price,
            productDescription, testShopUuid, product.getProductUuid(),
            testOwnerUser.getUserStringId(),
            testOwnerUser.getRole());

        Mockito.when(productOutputPort.findByProductUuid(product.getProductUuid()))
            .thenReturn(Optional.of(product));

        // When & Then
        ProductDeletedException exception = Assertions.assertThrows(
            ProductDeletedException.class,
            () -> productService.updateProduct(productForUpdate)
        );

        Assertions.assertEquals(
            String.format("삭제된 상품 입니다.: %s", product.getProductUuid()),
            exception.getMessage()
        );

        Mockito.verify(productOutputPort, Mockito.times(1))
            .findByProductUuid(product.getProductUuid());
        Mockito.verify(productOutputPort, Mockito.never()).updateProduct(ArgumentMatchers.any());
    }
}
