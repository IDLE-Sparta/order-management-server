package com.sparta.ordermanagement.application.service.product;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.output.ProductOutputPort;
import com.sparta.ordermanagement.application.service.ProductService;
import com.sparta.ordermanagement.application.service.ShopService;
import com.sparta.ordermanagement.application.service.UserService;
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
    protected ShopCategory shopCategory;
    protected Shop shop;
    protected User owner;

    @BeforeEach
    void setUp() {
        regionEntity = new RegionEntity();
        owner = TestDataForProduct.createUser("owner1", Role.OWNER, regionEntity);
        shopCategory = TestDataForProduct.createShopCategory("category-uuid", "치킨");
        shop = TestDataForProduct.createShop("shop-uuid", shopCategory, "소현이네 치킨집", owner.getUserStringId());
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
