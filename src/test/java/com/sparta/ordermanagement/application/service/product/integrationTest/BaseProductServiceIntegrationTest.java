package com.sparta.ordermanagement.application.service.product.integrationTest;

import com.sparta.ordermanagement.application.service.product.ProductService;
import com.sparta.ordermanagement.application.service.TestDataForIntegrationTest;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import com.sparta.ordermanagement.framework.persistence.repository.product.ProductRepository;
import com.sparta.ordermanagement.framework.persistence.repository.region.RegionRepository;
import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopCategoryRepository;
import com.sparta.ordermanagement.framework.persistence.repository.shop.ShopRepository;
import com.sparta.ordermanagement.framework.persistence.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class BaseProductServiceIntegrationTest {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShopCategoryRepository shopCategoryRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected RegionRepository regionRepository;

    protected RegionEntity regionEntity;

    protected UserEntity ownerEntity;

    protected UserEntity customerEntity;

    protected ShopCategoryEntity shopCategoryEntity;

    protected ShopEntity shopEntity;

    protected ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        regionEntity = regionRepository.save(TestDataForIntegrationTest.createRegionEntity());

        ownerEntity = userRepository.save(
            TestDataForIntegrationTest.createUserEntity("owner", Role.OWNER, regionEntity));

        customerEntity = userRepository.save(
            TestDataForIntegrationTest.createUserEntity("customer", Role.CUSTOMER, regionEntity));

        shopCategoryEntity = shopCategoryRepository.save(
            TestDataForIntegrationTest.createShopCategoryEntity("치킨"));

        shopEntity = shopRepository.save(
            TestDataForIntegrationTest.createShopEntity("shop-uuid", ownerEntity,
                "소현이네 치킨집", shopCategoryEntity));

        productEntity = productRepository.save(
            TestDataForIntegrationTest.createProductEntity("product-uuid", "후라이드",
                10_000, "맛있는 후라이드", shopEntity)
        );
    }
}
