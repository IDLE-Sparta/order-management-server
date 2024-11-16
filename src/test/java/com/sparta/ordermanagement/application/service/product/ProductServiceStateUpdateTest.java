package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductStateForUpdate;
import com.sparta.ordermanagement.application.service.TestData;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceStateUpdateTest extends BaseProductServiceTest {

    private String productUuid;
    private String productName;
    private Integer productPrice;

    private Product existProduct;

    @BeforeEach
    void setUp() {
        super.setUp();
        productUuid = "product-uuid";
        productName = "후라이드";
        productPrice = 10_000;

        existProduct = TestData.createProduct(productUuid, productName, productPrice,
            shop);
    }

    @Test
    @DisplayName("[상품 상태 수정 성공 테스트] OWNER 권한을 가진 사용자가 상품 상태를 수정하려고 할 때 변경된 상품 상태를 확인한다.")
    public void stateUpdateProduct_successTest() {
        // Given
        Product expectedProduct = TestData.createHiddenProduct(
            existProduct.getProductUuid(), existProduct.getProductName(),
            existProduct.getProductPrice(), shop);

        ProductStateForUpdate productStateForUpdate = new ProductStateForUpdate(ProductState.HIDE,
            shop.getUuid(), existProduct.getProductUuid(), owner.getUserStringId(),
            owner.getRole());

        Mockito.when(productOutputPort.findByProductUuid(productStateForUpdate.productUuid()))
            .thenReturn(Optional.of(existProduct));
        Mockito.when(productOutputPort.updateProductState(productStateForUpdate)).thenReturn(expectedProduct);

        // When
        Product actualProduct = productService.updateProductState(productStateForUpdate);

        // Then
        Assertions.assertAll(
            "Product state 필드 검증",
            () -> Assertions.assertNotNull(actualProduct, "실제 Product가 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedProduct.getProductState(),
                actualProduct.getProductState(), "Product state가 일치하지 않습니다.")
        );

        Mockito.verify(productOutputPort, Mockito.times(1))
            .updateProductState(productStateForUpdate);
    }
}
