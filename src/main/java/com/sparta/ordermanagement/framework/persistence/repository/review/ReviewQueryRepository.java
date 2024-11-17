package com.sparta.ordermanagement.framework.persistence.repository.review;

import static com.sparta.ordermanagement.framework.persistence.entity.review.QReviewEntity.reviewEntity;
import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopEntity.shopEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ordermanagement.framework.persistence.entity.review.ReviewEntity;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {

    private static final Long DEFAULT_REVIEW_ID = 0L;

    private final JPAQueryFactory jpaQueryFactory;

    public List<ReviewEntity> findAllByShopUuidAndIsDeletedFalse(String shopUuid, Cursor cursor) {

        Long basedReviewId = getBasedReviewId(cursor);

        if (cursor.isDefaultSort()) {
            return findAllByShopUuidAndIsDeletedFalseOrderByCreatedAt(basedReviewId, shopUuid, cursor.size());
        }
        return findAllByShopUuidAndIsDeletedFalseOrderByRating(basedReviewId, shopUuid, cursor);
    }

    private Long getBasedReviewId(Cursor cursor) {
        if (cursor.isFirstPage()) {
            return DEFAULT_REVIEW_ID;
        }
        return Optional.ofNullable(jpaQueryFactory.select(reviewEntity)
                .from(reviewEntity)
                .where(reviewEntity.reviewUuid.eq(cursor.basedUuid()))
                .fetchOne())
            .orElseThrow(IllegalArgumentException::new)
            .getId();
    }

    /*
    select * from p_review r
    inner join p_shop s on s.uuid = :shopUuid
    where r.shop_entity_shop_id = s.shop_id
        and r.is_deleted = false
        and r.review_id > :basedReviewId
    order by r.review_id desc, r.created_at desc
    limit :size;
    */
    private List<ReviewEntity> findAllByShopUuidAndIsDeletedFalseOrderByCreatedAt(Long basedReviewId, String shopUuid, int size) {
        return jpaQueryFactory.selectFrom(reviewEntity)
            .where(
                reviewEntity.shopEntity.shopUuid.eq(shopUuid),
                reviewEntity.isDeleted.eq(false),
                reviewEntity.id.gt(basedReviewId)
            )
            .orderBy(reviewEntity.id.desc(), reviewEntity.createdAt.desc())
            .limit(size)
            .fetch();
    }

    /*
    select * from p_review r
    inner join p_shop s on s.uuid = :shopUuid
    where r.shop_entity_shop_id = s.shop_id
        and r.is_deleted = false
        and r.review_id > :basedReviewId
    order by r.rating desc, r.id desc
    limit :size;
    */
    private List<ReviewEntity> findAllByShopUuidAndIsDeletedFalseOrderByRating(Long basedReviewId, String shopUuid, Cursor cursor) {
        return jpaQueryFactory.selectFrom(reviewEntity)
            .where(
                reviewEntity.shopEntity.shopUuid.eq(shopUuid),
                reviewEntity.isDeleted.eq(false),
                reviewEntity.id.gt(basedReviewId)
            )
            .orderBy(reviewEntity.rating.desc(), reviewEntity.id.desc())
            .limit(cursor.size())
            .fetch();
    }
}
