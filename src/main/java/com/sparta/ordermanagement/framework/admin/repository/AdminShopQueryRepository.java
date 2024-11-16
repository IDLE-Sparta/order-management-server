package com.sparta.ordermanagement.framework.admin.repository;

import static com.sparta.ordermanagement.framework.persistence.entity.shop.QShopEntity.*;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.ordermanagement.bootstrap.admin.dto.ShopSearchCondition;
import com.sparta.ordermanagement.framework.persistence.entity.shop.ShopEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminShopQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<ShopEntity> findAll(ShopSearchCondition searchCondition, Pageable pageable) {

        BooleanExpression searchExpression = getSearchCondition(searchCondition);
        BooleanExpression categoryExpression = getCategoryCondition(searchCondition);
        BooleanExpression containingDeletedExpression = getContainDeletedCondition(searchCondition);

        List<ShopEntity> content = jpaQueryFactory.selectFrom(shopEntity)
            .join(shopEntity.shopCategoryEntity).fetchJoin()
            .join(shopEntity.userEntity).fetchJoin()
            .where(searchExpression, categoryExpression, containingDeletedExpression)
            .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
            .offset(pageable.getPageNumber())
            .limit(pageable.getPageSize())
            .fetch();

        long total = Optional.ofNullable(jpaQueryFactory.select(shopEntity.count())
                .from(shopEntity)
                .where(searchExpression, categoryExpression, containingDeletedExpression)
                .fetchOne()).orElse((long) 0);

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression getSearchCondition(ShopSearchCondition searchCondition) {
        if (!searchCondition.isContainKeyword()) {
            return null;
        }
        return shopEntity.shopName.containsIgnoreCase(searchCondition.getKeyword());
    }

    private BooleanExpression getCategoryCondition(ShopSearchCondition searchCondition) {
        if (!searchCondition.isContainCategoryId()) {
            return null;
        }
        return shopEntity.shopCategoryEntity.id.eq(searchCondition.getCategoryId());
    }

    private BooleanExpression getContainDeletedCondition(ShopSearchCondition searchCondition) {
        if (searchCondition.isContainingDeleted()) {
            return null;
        }
        return shopEntity.isDeleted.isFalse();
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            PathBuilder<ShopEntity> orderByExpression = new PathBuilder<>(ShopEntity.class, "shopEntity");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(property)));
        });
        return orders;
    }
}
