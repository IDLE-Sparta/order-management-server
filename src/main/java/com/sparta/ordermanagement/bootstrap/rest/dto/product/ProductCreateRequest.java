package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.ProductForCreate;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    String productName;

    @NotNull(message = "가격을 입력해주세요.")
    @Positive(message = "가격은 0원 이상으로 설정해야 합니다.")
    @Max(value = 2_000_000_000, message = "가격은 2_000_000_000 이하로 설정해야 합니다.")
    Integer productPrice;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    String productDescription;

    ProductState productState;

    public ProductForCreate toDomain(String shopUuid, String userId) {
        return new ProductForCreate(
            productName,
            productPrice,
            productDescription,
            productState,
            shopUuid,
            userId
        );
    }
}
