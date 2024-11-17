package com.sparta.ordermanagement.bootstrap.admin.dto;

import com.sparta.ordermanagement.application.admin.vo.ShopForCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SHOP_REQ_01 : 가게 생성 요청 DTO")
public class ShopCreateRequest {

    @NotBlank(message = "가게 카테고리 아이디를 입력해주세요.")
    @Schema(description = "가게 카테고리 아이디")
    String shopCategoryId;

    @NotBlank(message = "가게 이름을 입력해주세요.")
    @Size(min = 2, max = 15, message = "가게 이름은 2글자 이상 15글자 이하로 입력해야 합니다.")
    @Schema(description = "가게 이름", example = "현수네 직화 김치제육")
    String shopName;

    @NotBlank(message = "가게 소유 유저 식별자를 입력해주세요.")
    @Schema(description = "가게 소유 유저 식별자")
    String ownerUserId;

    public ShopForCreate toDomain(String createdUserId) {
        return new ShopForCreate(shopCategoryId, shopName, ownerUserId, createdUserId);
    }
}
