package com.sparta.ordermanagement.framework.persistence.vo;

import java.util.Objects;

public record Cursor(int size, String basedUuid, String basedValue, Sort sort) {

    public boolean isFirstPage() {
        return Objects.isNull(basedUuid) || basedUuid.isBlank();
    }

    public boolean isDefaultSort() {
        return sort.isDefaultSort();
    }
}
