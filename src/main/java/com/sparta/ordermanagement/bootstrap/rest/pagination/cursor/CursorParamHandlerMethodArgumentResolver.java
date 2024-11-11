package com.sparta.ordermanagement.bootstrap.rest.pagination.cursor;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CursorParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CursorPagination.class) &&
            parameter.hasParameterAnnotation(CursorRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String size = webRequest.getParameter("size");
        String basedUuid = webRequest.getParameter("basedUuid");
        String basedValue = webRequest.getParameter("basedValue");
        String sortedColumn = webRequest.getParameter("sortedColumn");

        return CursorPagination.of(size, basedUuid, basedValue, sortedColumn);
    }
}
