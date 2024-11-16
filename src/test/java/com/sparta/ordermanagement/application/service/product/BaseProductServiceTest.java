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
    protected String testProductUuid;

    protected String testOwnerUserStringId;
    protected String testCustomerUserStringId;

    protected ShopCategory testShopCategory;
    protected Shop testShop;
    protected User testOwnerUser;
    protected User testCustomerUser;
    protected Product expectedProduct;

    @BeforeEach
    void setUp() {
        testShopCategoryUuid = "4c4f79f2-7335-4acd-9eaa-f2f8f3db506e";
        testShopUuid = "0e9518ff-13ec-447d-bd08-e3915841cd49";
        testProductUuid = "7feaff71-613f-4c67-b25c-9210c2c9f429";

        testOwnerUserStringId = "owner1";
        testCustomerUserStringId = "customer1";

        regionEntity = new RegionEntity();

        testShopCategory = new ShopCategory(testShopCategoryUuid, "치킨");
        testShop = new Shop(1L, testShopUuid, testShopCategory, "소현이네 치킨집", 4.0);
        testOwnerUser = new User(1L, testOwnerUserStringId, "qwer1234#", Role.OWNER, regionEntity);
        testCustomerUser = new User(2L, testCustomerUserStringId, "qwer1234#", Role.CUSTOMER,
            regionEntity);

        expectedProduct = new Product(1L, testProductUuid, "후라이드", 20_000, "맛있는 후라이드",
            ProductState.SHOW, testShop, false);
    }

    @InjectMocks
    protected ProductService productService;

    @Mock
    protected ShopService shopService;

    @Mock
    protected UserService userService;

    @Mock
    protected ProductOutputPort productOutputPort;
}
