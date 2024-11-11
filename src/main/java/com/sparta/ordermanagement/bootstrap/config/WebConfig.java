package com.sparta.ordermanagement.bootstrap.config;

import com.sparta.ordermanagement.bootstrap.rest.pagination.cursor.CursorParamHandlerMethodArgumentResolver;
import com.sparta.ordermanagement.bootstrap.rest.pagination.offset.PaginationArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final PaginationArgumentResolver paginationArgumentResolver;
    private final CursorParamHandlerMethodArgumentResolver cursorParamHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(cursorParamHandlerMethodArgumentResolver);
        resolvers.add(paginationArgumentResolver);
    }
}
