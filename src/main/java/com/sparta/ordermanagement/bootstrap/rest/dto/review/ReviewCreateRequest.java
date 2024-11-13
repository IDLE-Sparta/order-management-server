package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
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
public class ReviewCreateRequest {

    @NotNull(message = "별점을 입력해주세요.")
    @Positive(message = "별점은 0이상 입력해주세요.")
    @Max(value = 5, message = "별점은 5이하로 입력해주세요.")
    private Integer rating;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String content;

    public ReviewForCreate toDomain(String orderUuid, String userUuid) {
        return new ReviewForCreate(
            rating,
            content,
            orderUuid,
            userUuid
        );
    }
}
