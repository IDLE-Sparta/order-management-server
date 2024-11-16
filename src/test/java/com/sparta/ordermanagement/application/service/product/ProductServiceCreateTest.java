package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.application.exception.user.UserAccessDeniedException;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceCreateTest extends BaseProductServiceTest{

    private String productName;
    private int productPrice;
    private String productDescription;

    @BeforeEach
    void setUp() {
        productName = "후라이드";
        productPrice = 20_000;
        productDescription = "맛있는 후라이드";
    }

    @Test
    @DisplayName("[상품 생성 성공 테스트] OWNER의 권한을 가지고 있는 사용자가 삭제되지 않는 가게의 상품을 생성할 경우 상품 식별자와 가게 식별자를 반환한다.")
    public void createProduct_successTest() {
        // Given
        ProductForCreate productForCreate = new ProductForCreate(productName, productPrice,
            productDescription, ProductState.SHOW, testShopUuid, testOwnerUser.getUserStringId(),
            testOwnerUser.getRole());

        Mockito.doNothing().when(userService).validateOwnerRole(ArgumentMatchers.eq(Role.OWNER));
        Mockito.doNothing().when(shopService).validateNotDeletedShopUuid(productForCreate.shopUuid());

        Mockito.when(productOutputPort.saveProduct(ArgumentMatchers.any(ProductForCreate.class)))
            .thenReturn(expectedProduct);

        // When

        Product createdProduct = productService.createProduct(productForCreate);

        // Then
        Assertions.assertAll(
            () -> Assertions.assertNotNull(createdProduct),
            () -> Assertions.assertEquals(expectedProduct.getProductUuid(), createdProduct.getProductUuid()),
            () -> Assertions.assertEquals(expectedProduct.getShop().getUuid(), createdProduct.getShop().getUuid())
        );

        Mockito.verify(productOutputPort, Mockito.times(1)).saveProduct(productForCreate);
    }

    @Test
    @DisplayName("[상품 생성 실패 테스트] OWNER 권한이 없는 사용자가 상품을 생성하려고 할 경우 예외를 발생시킨다.")
    public void createProduct_failureTest_notOwnerRole() {
        // Given
        ProductForCreate productForCreate = new ProductForCreate(productName, productPrice,
            productDescription, ProductState.SHOW, testShopUuid, testCustomerUser.getUserStringId(),
            testCustomerUser.getRole());

        Mockito.doThrow(new UserAccessDeniedException(testCustomerUser.getRole()))
            .when(userService).validateOwnerRole(ArgumentMatchers.argThat(role -> role != Role.OWNER));

        // When & Then
        UserAccessDeniedException exception = Assertions.assertThrows(
            UserAccessDeniedException.class,
            () -> productService.createProduct(productForCreate)
        );

        Assertions.assertEquals(String.format("접근 권한이 없습니다.: %s", testCustomerUser.getRole()), exception.getMessage());

        Mockito.verify(userService, Mockito.times(1)).validateOwnerRole(testCustomerUser.getRole());
        Mockito.verifyNoInteractions(shopService, productOutputPort);
    }
}