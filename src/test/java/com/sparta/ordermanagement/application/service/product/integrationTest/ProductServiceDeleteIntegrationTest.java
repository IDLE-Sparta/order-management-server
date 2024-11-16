package com.sparta.ordermanagement.application.service.product.integrationTest;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForDelete;
import com.sparta.ordermanagement.application.exception.user.UserAccessDeniedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductServiceDeleteIntegrationTest extends BaseProductServiceIntegrationTest {

    @Test
    @DisplayName("[상품 삭제 성공 통합 테스트] OWNER 권한의 사용자가 자신이 생성한 상품을 삭제할 때 상품의 삭제여부를 확인하다.")
    public void deletedProduct_successTest() {
        ProductForDelete productForDelete = new ProductForDelete(
            true,
            shopEntity.getShopUuid(),
            productEntity.getProductUuid(),
            ownerEntity.getUserStringId(),
            ownerEntity.getRole()
        );

        Product deletedProduct = productService.deleteProduct(productForDelete);

        Assertions.assertAll(
            () -> Assertions.assertNotNull(deletedProduct),
            () -> Assertions.assertTrue(deletedProduct.getIsDeleted())
        );
    }

    @Test
    @DisplayName("[상품 삭제 실패 통합 테스트] OWNER 권한이 없는 사용자가 상품을 삭제할 때 예외처리를 한다.")
    public void deletedProduct_failureTest_notOwnerRole() {
        ProductForDelete productForDelete = new ProductForDelete(
            true,
            shopEntity.getShopUuid(),
            productEntity.getProductUuid(),
            customerEntity.getUserStringId(),
            customerEntity.getRole()
        );

        UserAccessDeniedException exception = Assertions.assertThrows(
            UserAccessDeniedException.class,
            () -> productService.deleteProduct(productForDelete)
        );

        Assertions.assertEquals(
            String.format("접근 권한이 없습니다.: %s", productForDelete.userRole()),
            exception.getMessage()
        );
    }
}
