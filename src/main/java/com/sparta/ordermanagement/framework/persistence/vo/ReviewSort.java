package com.sparta.ordermanagement.framework.persistence.vo;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewSort implements Sort {

    CREATED_AT("created_at", LocalDateTime.class),
    RATING("rating", Integer.class);

    private final String columnName;
    private final Class<?> valueType;

    public static ReviewSort of(String basedColumn) {
        for (ReviewSort reviewSort : ReviewSort.values()) {
            if (Objects.equals(reviewSort.columnName, basedColumn)) {
                return reviewSort;
            }
        }
        return CREATED_AT;
    }

    @Override
    public boolean isDefaultSort() {
        return Objects.equals(columnName, Sort.DEFAULT_SORT);
    }
}
