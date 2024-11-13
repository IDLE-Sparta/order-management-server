package com.sparta.ordermanagement.application.domain.review;

import com.sparta.ordermanagement.application.domain.shop.Shop;

public class Review {

    // TODO: User 추가하기

    private Long id;
    private String reviewUuid;
    private Integer rating;
    private String content;
    private Shop shop;
//    private User user;
    private boolean isDeleted;

    public Review(
        Long id,
        String reviewUuid,
        Integer rating,
        String content,
        Shop shop,
//        User user,
        boolean isDeleted
    ) {
        this.id = id;
        this.reviewUuid = reviewUuid;
        this.rating = rating;
        this.content = content;
        this.shop = shop;
//        this.user = user;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public String getReviewUuid() {
        return reviewUuid;
    }

    public double getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Shop getShop() {
        return shop;
    }

//    public User getUser() {
//        return user;
//    }

    public boolean getIsDeleted() {
        return isDeleted;
    }
}
