package com.sparta.ordermanagement.bootstrap.rest.dto.product;

import com.sparta.ordermanagement.application.domain.product.ProductForUpdate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    private String productName;

    @Positive(message = "가격은 0이상으로 설정해야 합니다.")
    @Max(value = 2_000_000_000, message = "가격은 2_000_000_000 이하로 설정해야 합니다.")
    private Integer productPrice;

    private String productDescription;

    public ProductForUpdate toDomain(
        String shopUuid,
        String productUuid,
        String userStringId
    ) {
        return new ProductForUpdate(
            productName,
            productPrice,
            productDescription,
            shopUuid,
            productUuid,
            userStringId
        );
    }
}
