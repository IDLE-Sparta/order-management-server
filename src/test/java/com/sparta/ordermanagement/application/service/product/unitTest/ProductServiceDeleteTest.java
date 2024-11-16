package com.sparta.ordermanagement.application.service.product.unitTest;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForDelete;
import com.sparta.ordermanagement.application.service.TestData;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceDeleteTest extends BaseProductServiceTest {

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
    @DisplayName("[상품 삭제 성공 테스트] OWNER 권한을 가진 사용자가 상품을 삭제하려고 할 때 삭제된 상품인지를 확인한다.")
    public void deleteProduct_successTest() {
        // Given
        Product expectedProduct = TestData.createDeleteProduct(productUuid, productName,
            productPrice, shop);

        ProductForDelete productForDelete = new ProductForDelete(true, shop.getUuid(),
            existProduct.getProductUuid(), owner.getUserStringId(), owner.getRole());

        Mockito.when(productOutputPort.findByProductUuid(productForDelete.productUuid()))
            .thenReturn(Optional.of(existProduct));
        Mockito.when(productOutputPort.deleteProduct(productForDelete)).thenReturn(expectedProduct);

        // When
        Product actualProduct = productService.deleteProduct(productForDelete);

        // Then
        Assertions.assertAll(
            "Product isDeleted 필드 검증",
            () -> Assertions.assertNotNull(actualProduct, "실제 Product는 null이 아니어야 합니다."),
            () -> Assertions.assertEquals(expectedProduct.getIsDeleted(),
                actualProduct.getIsDeleted())
        );

        Mockito.verify(productOutputPort, Mockito.times(1))
            .deleteProduct(productForDelete);
    }
}
