package com.sparta.ordermanagement.application.service;

import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.SHOP_ID_INVALID;
import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.SHOP_OWNER_MISMATCH;
import static org.assertj.core.api.Assertions.*;

import com.sparta.ordermanagement.application.domain.shop.Shop;
import com.sparta.ordermanagement.application.domain.shop.ShopForUpdate;
import com.sparta.ordermanagement.application.domain.user.User;
import com.sparta.ordermanagement.application.exception.shop.ShopIdInvalidException;
import com.sparta.ordermanagement.application.exception.shop.ShopOwnerMismatchException;
import com.sparta.ordermanagement.application.output.shop.ShopOutputPort;
import com.sparta.ordermanagement.application.service.shop.ShopCategoryService;
import com.sparta.ordermanagement.application.service.shop.ShopService;
import com.sparta.ordermanagement.application.service.user.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    private static final String TEST_SHOP_CATEGORY_ID = "shopCategoryId";
    private static final String TEST_SHOP_NAME = "shopName";
    private static final String TEST_USER_ID = "userId";
    private static final User TEST_USER = new User(1L, TEST_USER_ID, null, null, null);

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopOutputPort shopOutputPort;

    @Mock
    private ShopCategoryService shopCategoryService;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("[가게 수정 실패 테스트] 존재하지 않은 가게 식별자인 경우 예외를 발생한다.")
    public void shopUpdate_FailureTest_GivenInvalidShopUuid() {
        // Given
        String invalidUuid = "InvalidUuid";
        ShopForUpdate shopForUpdate = new ShopForUpdate(
            invalidUuid, TEST_SHOP_CATEGORY_ID, TEST_SHOP_NAME, TEST_USER_ID);

        shopOutputPortFindByIdReturnEmptyOptional();

        // When & Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> shopService.updateShop(shopForUpdate))
                .isInstanceOf(ShopIdInvalidException.class)
                .hasMessage(String.format(SHOP_ID_INVALID.getMessage(), invalidUuid))
        );
    }

    private void shopOutputPortFindByIdReturnEmptyOptional() {
        Mockito.when(shopOutputPort.findById(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("[가게 수정 실패 테스트] 요청자가 가게 소유자가 아닌 경우 예외를 발생한다.")
    public void shopUpdate_FailureTest_GivenMismatchedUserId() {
        // Given
        String invalidUserId = "i'm not owner";
        ShopForUpdate shopForUpdate = new ShopForUpdate(
            "testUuid", TEST_SHOP_CATEGORY_ID, TEST_SHOP_NAME, invalidUserId);

        shopOutputPortFindByIdReturnTestShop(invalidUserId);
        userServiceFindByIdReturnTestUser();

        // When & Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> shopService.updateShop(shopForUpdate))
                .isInstanceOf(ShopOwnerMismatchException.class)
                .hasMessage(String.format(SHOP_OWNER_MISMATCH.getMessage(), invalidUserId))
        );
    }

    private void userServiceFindByIdReturnTestUser() {
        Mockito.when(userService.findByUserStringId(ArgumentMatchers.anyString()))
            .thenReturn(TEST_USER);
    }

    private void shopOutputPortFindByIdReturnTestShop(String ownerStringId) {
        Shop shop = new Shop(null, null, null, null, 0.0, ownerStringId);
        Mockito.when(shopOutputPort.findById(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(shop));
    }
}