package com.sparta.ordermanagement.bootstrap.rest.pagination.offset;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PaginationConstraint {

    int[] availableSizes() default {10, 30, 50};
    int defaultSize() default 10;
    String defaultSort() default "createdAt";
    String defaultDirection() default "ASC";
}

