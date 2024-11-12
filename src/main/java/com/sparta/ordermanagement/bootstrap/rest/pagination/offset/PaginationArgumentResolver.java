package com.sparta.ordermanagement.bootstrap.rest.pagination.offset;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PaginationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public Pageable resolveArgument(
        MethodParameter methodParameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        final Pageable pageable =
            super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        if (methodParameter.hasMethodAnnotation(PaginationConstraint.class)) {
            return validatePageableOrDefault(methodParameter, pageable);
        }
        return pageable;
    }

    private static Pageable validatePageableOrDefault(MethodParameter methodParameter, Pageable pageable) {

        PaginationConstraint paginationConstraint =
            methodParameter.getMethodAnnotation(PaginationConstraint.class);

        Sort sort = validateSortAndGet(pageable, paginationConstraint);
        int size = validateSizeAndGet(pageable, paginationConstraint);

        return PageRequest.of(pageable.getPageNumber(), size, sort);
    }

    private static Sort validateSortAndGet(Pageable pageable, PaginationConstraint paginationConstraint) {
        if (pageable.getSort().isSorted()) {
            return pageable.getSort();
        }
        return Sort.by(Sort.Direction.fromString(paginationConstraint.defaultDirection()), paginationConstraint.defaultSort());
    }

    private static int validateSizeAndGet(Pageable pageable, PaginationConstraint paginationConstraint) {
        int[] availableSizes = paginationConstraint.availableSizes();
        for (int availableSize : availableSizes) {
            if (pageable.getPageSize() == availableSize) {
                return pageable.getPageSize();
            }
        }
        return paginationConstraint.defaultSize();
    }
}
