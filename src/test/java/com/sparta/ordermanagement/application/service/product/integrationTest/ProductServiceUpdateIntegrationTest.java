package com.sparta.ordermanagement.application.service.product.integrationTest;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import com.sparta.ordermanagement.application.exception.product.ProductUuidInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductServiceUpdateIntegrationTest extends BaseProductServiceIntegrationTest {

    @Test
    @DisplayName("[상품 수정 성공 테스트] OWNER 권한의 사용자가 자신이 생성한 상품을 수정하면 수정된 상품을 돌려준다.")
    public void updateProduct_successTest() {
        ProductForUpdate productForUpdate = new ProductForUpdate(
            "양념치킨",
            15_000,
            "맛있는 양념치킨",
            shopEntity.getShopUuid(),
            productEntity.getProductUuid(),
            ownerEntity.getUserStringId(),
            ownerEntity.getRole()
        );

        Product updatedProduct = productService.updateProduct(productForUpdate);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(updatedProduct),
            () -> Assertions.assertEquals("양념치킨", updatedProduct.getProductName()),
            () -> Assertions.assertEquals(15_000, updatedProduct.getProductPrice()),
            () -> Assertions.assertEquals("맛있는 양념치킨", updatedProduct.getProductDescription())
        );
    }

    @Test
    @DisplayName("[상품 수정 실패 통합 테스트] OWNER 권한의 유효하지 않은 상품의 식별자를 사용하여 상품을 수정할 때 예외처리를 한다.")
    public void updateProduct_failureTest_invalidProductUuid() {
        String invalidProductUuid = "invalid-product-uuid";
        ProductForUpdate productForUpdate = new ProductForUpdate(
            "양념치킨",
            15_000,
            "맛있는 양념치킨",
            shopEntity.getShopUuid(),
            invalidProductUuid,
            ownerEntity.getUserStringId(),
            ownerEntity.getRole()
        );

        ProductUuidInvalidException exception = Assertions.assertThrows(
            ProductUuidInvalidException.class,
            () -> productService.updateProduct(productForUpdate)
        );

        Assertions.assertEquals(
            String.format("유효하지 않은 상품 식별자 입니다: %s", productForUpdate.productUuid()),
            exception.getMessage()
        );
    }
}
