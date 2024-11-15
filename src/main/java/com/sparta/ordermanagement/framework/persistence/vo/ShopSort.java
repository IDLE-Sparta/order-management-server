package com.sparta.ordermanagement.framework.persistence.vo;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopSort implements Sort {

    CREATED_AT("created_at", LocalDate.class),
    RATING("rating", Double.class),
    SHOP_NAME("shop_name", String.class);

    private static final ShopSort DEFAULT_SORT = CREATED_AT;

    private String columnName;
    private Class<?> valueType;

    public static ShopSort of(String basedColumn) {
        for (ShopSort shopSort : ShopSort.values()) {
            if (Objects.equals(shopSort.columnName, basedColumn)) {
                return shopSort;
            }
        }
        return DEFAULT_SORT;
    }

    @Override
    public boolean isDefaultSort() {
        return Objects.equals(columnName, Sort.DEFAULT_SORT);
    }
}
