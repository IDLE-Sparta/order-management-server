package com.sparta.ordermanagement.application.service;

import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.CATEGORY_NAME_DUPLICATED;
import static org.assertj.core.api.Assertions.*;

import com.sparta.ordermanagement.application.domain.shop.ShopCategory;
import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import com.sparta.ordermanagement.application.exception.shop.ShopCategoryNameDuplicatedException;
import com.sparta.ordermanagement.application.output.ShopCategoryOutputPort;
import java.util.Optional;
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
class ShopCategoryServiceTest {

    private String testUuid;
    private ShopCategory testCategory;

    @BeforeEach
    void setUp() {
        testUuid = "123456";
        testCategory = new ShopCategory(testUuid, "테스트");
    }

    @InjectMocks
    private ShopCategoryService shopCategoryService;

    @Mock
    private ShopCategoryOutputPort shopCategoryOutputPort;

    @Test
    @DisplayName("[카테고리 저장 성공 테스트] 중복되지 않는 카테고리명이 주어진 경우 카테고리 식별자를 반환한다.")
    public void createCategory_successTest() {
        // Given
        String categoryName = "한식";
        ShopCategoryForCreate shopCategoryForCreate = new ShopCategoryForCreate(categoryName);

        // When
        Mockito.when(shopCategoryOutputPort.findByCategoryName(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        Mockito.when(shopCategoryOutputPort.saveCategory(ArgumentMatchers.any(ShopCategoryForCreate.class)))
            .thenReturn(testUuid);

        String categoryId = shopCategoryService.createCategory(shopCategoryForCreate);
        // Then
        Assertions.assertAll(
            () -> Assertions.assertEquals(testUuid, categoryId)
        );
    }

    @Test
    @DisplayName("[카테고리 저장 실패 테스트] 중복된 카테고리명이 주어진 경우 예외를 발생시킨다.")
    public void createCategory_failureTest_DuplicatedCategoryName() {
        // Given
        String duplicatedCategoryName = "한식";
        ShopCategoryForCreate shopCategoryForCreate = new ShopCategoryForCreate(duplicatedCategoryName);
        // When
        Mockito.when(shopCategoryOutputPort.findByCategoryName(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(testCategory));
        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> shopCategoryService.createCategory(shopCategoryForCreate))
                .isInstanceOf(ShopCategoryNameDuplicatedException.class)
                .hasMessage(CATEGORY_NAME_DUPLICATED.getMessage())
        );
    }
}