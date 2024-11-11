package com.sparta.ordermanagement.framework.persistence.repository;

import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopCategoryEntity.*;
import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopEntity.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ShopQueryRepository {

    private static final String KEYWORD_FORMAT = "%%%s%%";
    private static final Long DEFAULT_SHOP_ID = 0L;
    private static final double DEFAULT_RATING = 5.0;

    private final JPAQueryFactory jpaQueryFactory;

    public List<ShopEntity> findAllByKeyword(String requestKeyword, Cursor cursor) {

        String keyword = String.format(KEYWORD_FORMAT, requestKeyword);
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
            .orElseThrow(IllegalArgumentException::new)
            .getId();
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
            .where(shopNameLikeCondition(keyword).and(shopEntity.id.gt(basedShopId)))
            .limit(size)
            .orderBy(shopEntity.createdAt.asc())
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
            .where(nameCondition.and(cursorCondition))
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
        return shopEntity.shopName.like(keyword);
    }
}
