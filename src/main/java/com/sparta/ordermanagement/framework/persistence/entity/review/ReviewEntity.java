package com.sparta.ordermanagement.framework.persistence.entity.review;

import com.sparta.ordermanagement.application.domain.review.Review;
import com.sparta.ordermanagement.application.domain.review.ReviewForCreate;
import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_review")
public class ReviewEntity extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "uuid", columnDefinition = "varchar(255)")
    private String reviewUuid;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String content;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shopEntity;

    // TODO: User 추가하기

//    @JoinColumn(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private UserEntity userEntity;

    private ReviewEntity(
        Integer rating,
        String content,
        ShopEntity shopEntity,
//        UserEntity userEntity,
        String createUserId
    ) {
        super(createUserId, createUserId);
        this.rating = rating;
        this.content = content;
        this.shopEntity = shopEntity;
//        this.userEntity = userEntity;
    }

    @PrePersist
    private void prePersistence() {
        reviewUuid = UUID.randomUUID().toString();
    }

    public static ReviewEntity from(
        ReviewForCreate reviewForCreate,
        ShopEntity shopEntity
//        UserEntity userEntity
    ) {
        return new ReviewEntity(
            reviewForCreate.rating(),
            reviewForCreate.content(),
            shopEntity,
//            userEntity,
            reviewForCreate.userId()
        );
    }

    public Review toDomain() {
        return new Review(
            id,
            reviewUuid,
            rating,
            content,
            shopEntity.toDomain(),
//            userEntity.toDomain(),
            super.isDeleted()
        );
    }
}
