package com.sparta.ordermanagement.bootstrap.rest.dto.shop;

import com.sparta.ordermanagement.application.domain.shop.ShopCategoryForCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategoryCreateRequest {

    @NotBlank(message = "카테고리명은 필수 입력 값입니다.")
    public String categoryName;

    public ShopCategoryForCreate toDomain() {
        return new ShopCategoryForCreate(categoryName);
    }
}
