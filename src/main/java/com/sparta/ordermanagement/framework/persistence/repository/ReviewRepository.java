package com.sparta.ordermanagement.framework.persistence.repository;


import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

}
