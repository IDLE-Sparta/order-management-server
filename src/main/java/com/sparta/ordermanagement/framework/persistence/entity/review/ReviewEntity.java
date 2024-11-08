package com.sparta.ordermanagement.framework.persistence.entity.review;

import com.sparta.ordermanagement.framework.persistence.entity.BaseEntity;
import com.sparta.ordermanagement.framework.persistence.entity.user.UserEntity;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double rating;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String content;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shopEntity;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

}
