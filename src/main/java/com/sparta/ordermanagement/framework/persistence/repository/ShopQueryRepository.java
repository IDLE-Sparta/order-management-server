package com.sparta.ordermanagement.framework.persistence.repository;

import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopCategoryEntity.*;
import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopEntity.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import jakarta.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ShopQueryRepository {

    private static final Long DEFAULT_SHOP_ID = 0L;
    private static final double DEFAULT_RATING = 5.0;

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public List<ShopEntity> findAllByKeyword(String keyword, Cursor cursor) {

        Long basedShopId = getBasedShopId(cursor);

        if(cursor.isDefaultSort()) {
            return findAllByKeywordOrderByCreatedAt(keyword, basedShopId, cursor.size());
        }
        return findAllByKeywordOderByRating(keyword, basedShopId, cursor);
    }

    private Long getBasedShopId(Cursor cursor) {
        if (cursor.isFirstPage()) {
            return DEFAULT_SHOP_ID;
        }
        return Optional.ofNullable(jpaQueryFactory.select(shopEntity)
            .from(shopEntity)
            .where(shopEntity.shopUuid.eq(cursor.basedUuid()))
            .fetchOne())
            .map(ShopEntity::getId)
            .orElse(DEFAULT_SHOP_ID);
    }

    /*
    select * from p_shop s
    inner join p_shop_category sc on sc.shop_category_id = s.shop_category_entity_shop_category_id
    where s.shop_name like '%현수네%' and s.shop_id > 508
    order by s.shop_id asc, s.created_at asc
    limit 10;
    */
    private List<ShopEntity> findAllByKeywordOrderByCreatedAt(String keyword, Long basedShopId, int size) {
        return jpaQueryFactory.selectFrom(shopEntity)
            .innerJoin(shopCategoryEntity)
            .on(shopCategoryEntity.id.eq(shopEntity.shopCategoryEntity.id))
            .where(shopNameLikeCondition(keyword).and(shopEntity.id.gt(basedShopId))
                .and(shopEntity.isDeleted.isFalse()))
            .orderBy(shopEntity.id.asc(), shopEntity.createdAt.asc())
            .limit(size)
            .fetch();
    }

    /*
    SELECT *
    FROM p_shop s
    INNER JOIN p_shop_category sc ON sc.shop_category_id = s.shop_category_entity_shop_category_id
    WHERE s.shop_name LIKE '%현수네%'
      AND (s.rating < 4) OR (s.rating = 4 and s.shop_id > 524)
    ORDER BY rating DESC, shop_id ASC
    LIMIT 10;
    */
    private List<ShopEntity> findAllByKeywordOderByRating(String keyword, Long basedShopId, Cursor cursor) {

        double basedValue = validateRatingValueAndGet(cursor);

        BooleanExpression nameCondition = shopNameLikeCondition(keyword);
        BooleanExpression cursorCondition = shopEntity.rating.lt(basedValue)
                .or(shopEntity.rating.eq(basedValue).and(shopEntity.id.gt(basedShopId)));

        return jpaQueryFactory.selectFrom(shopEntity)
            .innerJoin(shopEntity.shopCategoryEntity, shopCategoryEntity)
            .where(nameCondition.and(cursorCondition)
                .and(shopEntity.isDeleted.isFalse()))
            .orderBy(shopEntity.rating.desc(), shopEntity.id.asc())
            .limit(cursor.size())
            .fetch();
    }

    private static double validateRatingValueAndGet(Cursor cursor) {
        try {
            return Double.parseDouble(cursor.basedValue());
        } catch (NumberFormatException exception) {
            return DEFAULT_RATING;
        }
    }

    private BooleanExpression shopNameLikeCondition(String keyword) {
        return shopEntity.shopName.containsIgnoreCase(keyword);
    }

    @Transactional
    public void updateShopRating(LocalDateTime time) {
        String query = """
            WITH updated_reviews AS (
                SELECT
                    shop_entity_shop_id,
                    COUNT(*) AS new_reviews_count,
                    AVG(rating) AS new_average_rating
                FROM p_review
                WHERE updated_at >= :time AND is_deleted = false
                GROUP BY shop_entity_shop_id
            ),
                 total_reviews AS (
                     SELECT
                         shop_entity_shop_id,
                         COUNT(*) AS total_reviews_count,
                         AVG(rating) AS overall_average_rating
                     FROM p_review
                     WHERE is_deleted = false
                     GROUP BY shop_entity_shop_id
                 )
                        
            UPDATE p_shop
            SET rating = ROUND((tr.total_reviews_count * tr.overall_average_rating + COALESCE(ur.new_reviews_count * ur.new_average_rating, 0))
                                   / (tr.total_reviews_count + COALESCE(ur.new_reviews_count, 0)), 1),
                review_count = tr.total_reviews_count + COALESCE(ur.new_reviews_count, 0)
            FROM updated_reviews ur
                     JOIN total_reviews tr ON ur.shop_entity_shop_id = tr.shop_entity_shop_id
            WHERE p_shop.shop_id = ur.shop_entity_shop_id;
            """;

        Timestamp timestamp = Timestamp.valueOf(time);
        entityManager.createNativeQuery(query)
            .setParameter("time", timestamp)
            .executeUpdate();
    }
}
