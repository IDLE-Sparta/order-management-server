package com.sparta.ordermanagement.application.service.product.integrationTest;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.application.exception.user.UserAccessDeniedException;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductServiceCreateIntegrationTest extends BaseProductServiceIntegrationTest {

    @Test
    @DisplayName("[상품 생성 성공 통합 테스트] OWNER의 권한을 가지고 있는 사용자가 삭제되지 않는 가게의 상품을 생성할 경우 상품을 반환한다.")
    public void createProduct_successTest() {
        ProductForCreate productForCreate = new ProductForCreate(
            "후라이드",
            10_000,
            "맛있는 후라이드",
            ProductState.SHOW,
            shopEntity.getShopUuid(),
            ownerEntity.getUserStringId(),
            ownerEntity.getRole()
        );

        Product product = productService.createProduct(productForCreate);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(product),
            () -> Assertions.assertEquals(productForCreate.productName(), product.getProductName()),
            () -> Assertions.assertEquals(productForCreate.productPrice(), product.getProductPrice()),
            () -> Assertions.assertEquals(productForCreate.productDescription(), product.getProductDescription()),
            () -> Assertions.assertEquals(productForCreate.productState(), product.getProductState()),
            () -> Assertions.assertEquals(productForCreate.shopUuid(), product.getShop().getUuid())
        );
    }

    @Test
    @DisplayName("[상품 생성 실패 통합 테스트] OWNER의 권한을 가지고 있지 않은 사용자가 가게의 상품을 생성할 경우 예외처리를 한다.")
    public void createProduct_failureTest_notOwnerRole() {
        ProductForCreate productForCreate = new ProductForCreate(
            "후라이드",
            10_000,
            "맛있는 후라이드",
            ProductState.SHOW,
            shopEntity.getShopUuid(),
            customerEntity.getUserStringId(),
            customerEntity.getRole()
        );

        UserAccessDeniedException exception = Assertions.assertThrows(
            UserAccessDeniedException.class,
            () -> productService.createProduct(productForCreate)
        );

        Assertions.assertEquals(
            String.format("접근 권한이 없습니다.: %s", productForCreate.userRole()),
            exception.getMessage()
        );
    }
}
