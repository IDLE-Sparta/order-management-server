package com.sparta.ordermanagement.bootstrap.rest.dto.review;

import com.sparta.ordermanagement.application.domain.review.ReviewForUpdate;
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
public class ReviewUpdateRequest {

    @Positive(message = "별점은 0이상 입력해주세요.")
    @Max(value = 5, message = "별점은 5이하로 입력해주세요.")
    private Integer rating;

    private String content;

    public ReviewForUpdate toDomain(
        String orderUuid,
        String reviewUuid,
        String userStringId
    ) {
        return new ReviewForUpdate(
            rating,
            content,
            orderUuid,
            reviewUuid,
            userStringId
        );
    }
}
