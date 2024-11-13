package com.sparta.ordermanagement.application.admin;

import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mapping.PropertyReferenceException;

public class PageableSortProxy {

    /**
     * 쿼리 실행 중 정렬 타입 불일치로 예외가 발생하면 <br>
     * 기본 정렬 전략으로 재 실행하는 메서드 <br><br>
     * param: Pageable : 쿼리에 적용할 페이지네이션 정보 <br>
     * param: Function : 실행할 메서드
     * */
    public static <T> Page<T> executeWithFallback(
        Pageable pageable, Function<Pageable, Page<T>> function) {

        try {
            return function.apply(pageable);

        } catch (PropertyReferenceException exception) {

            Pageable fallbackPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Direction.ASC, "createdAt")
            );
            return function.apply(fallbackPageable);
        }
    }
}