package com.sparta.ordermanagement.framework.persistence.repository.product;

import static com.sparta.ordermanagement.framework.persistence.entity.product.QProductEntity.productEntity;
import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopEntity.shopEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductEntity;
import com.sparta.ordermanagement.framework.persistence.entity.product.ProductState;
import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {

    private static final Long DEFAULT_PRODUCT_ID = 0L;

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProductEntity> findAllByShopUuidAndIsDeletedFalseAndProductStateShow(String shopUuid, Cursor cursor) {

        Long basedProductId = getBasedProductId(cursor);

        if (cursor.isDefaultSort()) {
            return findAllByShopUuidAndIsDeletedFalseAndProductStateShowByCreatedAt(shopUuid, basedProductId, cursor.size());
        }
        return findAllByShopUuidAndIsDeletedFalseAndProductStateShowByProductName(shopUuid, basedProductId, cursor.size());
    }

    private Long getBasedProductId(Cursor cursor) {
        if (cursor.isFirstPage()) {
            return DEFAULT_PRODUCT_ID;
        }

        return Optional.ofNullable(jpaQueryFactory.select(productEntity)
                .from(productEntity)
                .where(productEntity.productUuid.eq(cursor.basedUuid()))
                .fetchOne())
            .orElseThrow(IllegalArgumentException::new)
            .getId();
    }

    /*
    select *
    from p_product p
    inner join p_shop s on p.shop_entity_shop_id = s.shop_id
    where s.uuid = :shopUuid
      and p.is_deleted = false
      and p.product_state = 'SHOW'
      and p.product_id > :basedProductId
    order by p.product_id asc, p.created_at asc
    limit :size;
    */
    private List<ProductEntity> findAllByShopUuidAndIsDeletedFalseAndProductStateShowByCreatedAt(String shopUuid, Long basedProductId, int size) {

        BooleanExpression shopCondition = shopEntity.shopUuid.eq(shopUuid);
        BooleanExpression isNotDeleted = productEntity.isDeleted.eq(false);
        BooleanExpression isCondition = productEntity.id.gt(basedProductId);
        BooleanExpression productStateCondition = productEntity.productState.eq(ProductState.SHOW);

        return jpaQueryFactory.selectFrom(productEntity)
            .innerJoin(shopEntity)
            .on(shopEntity.id.eq(productEntity.shopEntity.id))
            .where(shopCondition.and(isNotDeleted).and(isCondition).and(productStateCondition))
            .orderBy(productEntity.id.asc(), productEntity.createdAt.asc())
            .limit(size)
            .fetch();
    }

    /*
    select *
    from p_product p
    inner join p_shop s on p.shop_entity_shop_id = s.shop_id
    where s.uuid = :shopUuid
      and p.is_deleted = false
      and p.product_state = 'SHOW'
      and p.product_id > :basedProductId
    order by p.product_id asc, p.product_name asc
    limit :size;
    */
    private List<ProductEntity> findAllByShopUuidAndIsDeletedFalseAndProductStateShowByProductName(String shopUuid, Long basedProductId, int size) {

        BooleanExpression shopCondition = shopEntity.shopUuid.eq(shopUuid);
        BooleanExpression isNotDeleted = productEntity.isDeleted.eq(false);
        BooleanExpression isCondition = productEntity.id.gt(basedProductId);
        BooleanExpression productStateCondition = productEntity.productState.eq(ProductState.SHOW);

        return jpaQueryFactory.selectFrom(productEntity)
            .innerJoin(shopEntity)
            .on(shopEntity.id.eq(productEntity.shopEntity.id))
            .where(shopCondition.and(isNotDeleted).and(isCondition).and(productStateCondition))
            .orderBy(productEntity.id.asc(), productEntity.productName.asc())
            .limit(size)
            .fetch();
    }
}
