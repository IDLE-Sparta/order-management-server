package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.product.Product;
import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.output.ProductOutputPort;
import com.sparta.ordermanagement.application.service.ProductService;
import com.sparta.ordermanagement.application.service.ShopService;
import com.sparta.ordermanagement.application.service.UserService;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseProductServiceTest {

    protected RegionEntity regionEntity;

    protected String testShopCategoryUuid;
    protected String testShopUuid;
    protected String testExistProductUuid;
    protected String testExpectedProductUuid;

    protected String testInvalidShopUuid;

    protected String testOwnerUserStringId;
    protected String testCustomerUserStringId;

    protected ShopCategory testShopCategory;
    protected Shop testShop;
    protected User testOwnerUser;
    protected User testCustomerUser;
    protected Product existProduct;
    protected Product expectedProduct;

    @BeforeEach
    void setUp() {
        testShopCategoryUuid = "4c4f79f2-7335-4acd-9eaa-f2f8f3db506e";
        testShopUuid = "0e9518ff-13ec-447d-bd08-e3915841cd49";
        testExistProductUuid = "99cf5c38-fd8d-42cd-881b-beb5c4d7a171";
        testExpectedProductUuid = "7feaff71-613f-4c67-b25c-9210c2c9f429";

        testInvalidShopUuid = "invalid-shop-uuid";

        testOwnerUserStringId = "owner1";
        testCustomerUserStringId = "customer1";

        regionEntity = new RegionEntity();

        testShopCategory = new ShopCategory(testShopCategoryUuid, "치킨");
        testShop = new Shop(1L, testShopUuid, testShopCategory, "소현이네 치킨집", 4.0);
        testOwnerUser = createUser(1L, testOwnerUserStringId, Role.OWNER);
        testCustomerUser = createUser(2L, testCustomerUserStringId, Role.CUSTOMER);

        existProduct = createProduct(1L, testExistProductUuid, "양념치킨", 10_000);
        expectedProduct = createProduct(2L, testExpectedProductUuid, "후라이드", 10_000);
    }

    @InjectMocks
    protected ProductService productService;

    @Mock
    protected ShopService shopService;

    @Mock
    protected UserService userService;

    @Mock
    protected ProductOutputPort productOutputPort;

    protected User createUser(Long id, String userStringId, Role role) {
        return new User(id, userStringId, "qwer1234#", role, regionEntity);
    }

    protected Product createProduct(Long id, String productUuid, String productName, int price) {
        return new Product(id, productUuid, productName, price, "맛있는 " + productName,
            ProductState.SHOW, testShop, false);
    }
}
