package com.sparta.ordermanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.output.ProductOutputPort;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private RegionEntity regionEntity;

    private String testShopCategoryUuid;
    private String testShopUuid;
    private String testUserStringId;
    private String testProductUuid;
    private ShopCategory testShopCategory;
    private Shop testShop;
    private User testUser;
    private Product expectedProduct;

    @BeforeEach
    void setUp() {
        testShopCategoryUuid = "4c4f79f2-7335-4acd-9eaa-f2f8f3db506e";
        testShopUuid = "0e9518ff-13ec-447d-bd08-e3915841cd49";
        testProductUuid = "7feaff71-613f-4c67-b25c-9210c2c9f429";

        testUserStringId = "owner1";

        regionEntity = new RegionEntity();

        testShopCategory = new ShopCategory(testShopCategoryUuid, "치킨");
        testShop = new Shop(1L, testShopUuid, testShopCategory, "소현이네 치킨집", 4.0);
        testUser = new User(1L, testUserStringId, "qwer1234#", Role.OWNER, regionEntity);

        expectedProduct = new Product(1L, testProductUuid, "후라이드", 20_000, "맛있는 후라이드",
            ProductState.SHOW, testShop, false);
    }

    @InjectMocks
    private ProductService productService;

    @Mock
    private ShopService shopService;

    @Mock
    private UserService userService;

    @Mock
    private ProductOutputPort productOutputPort;

    @Test
    @DisplayName("[상품 생성 성공 테스트] OWNER의 권한을 가지고 있는 사용자가 삭제되지 않는 가게의 상품을 생성할 경우 상품 식별자와 가게 식별자를 반환한다.")
    public void createProduct_successTest() {
        // Given
        String productName = "후라이드";
        int productPrice = 20_000;
        String productDescription = "맛있는 후라이드";
        ProductForCreate productForCreate = new ProductForCreate(productName, productPrice,
            productDescription, ProductState.SHOW, testShopUuid, testUser.getUserStringId(),
            testUser.getRole());

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
            () -> Assertions.assertEquals(expectedProduct.getProductName(), createdProduct.getProductName()),
            () -> Assertions.assertEquals(expectedProduct.getProductPrice(), createdProduct.getProductPrice()),
            () -> Assertions.assertEquals(expectedProduct.getProductDescription(), createdProduct.getProductDescription()),
            () -> Assertions.assertEquals(expectedProduct.getProductState(), createdProduct.getProductState()),
            () -> Assertions.assertEquals(expectedProduct.getShop().getUuid(), createdProduct.getShop().getUuid())
        );

        Mockito.verify(productOutputPort, Mockito.times(1)).saveProduct(productForCreate);
    }

}