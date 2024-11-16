package com.sparta.ordermanagement.application.service.review.integrationTest;

import com.sparta.ordermanagement.application.service.ReviewService;
import com.sparta.ordermanagement.application.service.TestDataForIntegrationTest;
import com.sparta.ordermanagement.framework.persistence.entity.order.OrderEntity;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.region.RegionEntity;
import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopCategoryEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.Role;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import com.sparta.ordermanagement.framework.persistence.repository.OrderRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ProductRepository;
import com.sparta.ordermanagement.framework.persistence.repository.RegionRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ReviewRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ShopCategoryRepository;
import com.sparta.ordermanagement.framework.persistence.repository.ShopRepository;
import com.sparta.ordermanagement.framework.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class BaseReviewServiceIntegrationTest {

    @Autowired
    protected ReviewService reviewService;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShopCategoryRepository shopCategoryRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected RegionRepository regionRepository;

    protected RegionEntity regionEntity;

    protected UserEntity ownerEntity;

    protected UserEntity customerEntity;

    protected ShopCategoryEntity shopCategoryEntity;

    protected ShopEntity shopEntity;

    protected ProductEntity productEntity;

    protected OrderEntity orderEntity;

    protected ReviewEntity reviewEntity;

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

        orderEntity = orderRepository.save(
            TestDataForIntegrationTest.createOrderEntity("order-uuid", shopEntity, customerEntity)
        );

        reviewEntity = reviewRepository.save(
            TestDataForIntegrationTest.createReviewEntity("review-uuid", 5, "진짜 맛있어요!",
                shopEntity, customerEntity)
        );
    }
}
