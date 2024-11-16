package com.sparta.ordermanagement.bootstrap.admin.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopSearchCondition {

    String keyword;
    String categoryId;
    Boolean containingDeleted;

    public boolean isContainKeyword() {
        return !(Objects.isNull(keyword) || keyword.isBlank());
    }

    public boolean isContainCategoryId() {
        return !(Objects.isNull(categoryId) || categoryId.isBlank());
    }

    public boolean isContainingDeleted() {
        return containingDeleted;
    }
}
